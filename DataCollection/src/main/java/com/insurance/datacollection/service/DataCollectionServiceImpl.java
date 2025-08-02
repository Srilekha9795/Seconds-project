package com.insurance.datacollection.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.insurance.datacollection.dto.ChildReqWrapper;
import com.insurance.datacollection.dto.ChildrenRequest;
import com.insurance.datacollection.dto.EducationReq;
import com.insurance.datacollection.dto.IncomeReq;
import com.insurance.datacollection.dto.PlanReq;
import com.insurance.datacollection.dto.PlanResponse;
import com.insurance.datacollection.dto.Summary;
import com.insurance.datacollection.dto.UserRegisteredSsnResponse;
import com.insurance.datacollection.entity.Children;
import com.insurance.datacollection.entity.DcCase;
import com.insurance.datacollection.entity.Education;
import com.insurance.datacollection.entity.Income;
import com.insurance.datacollection.entity.Plan;
import com.insurance.datacollection.exception.AppIdErrorException;
import com.insurance.datacollection.exception.CaseNumNotFoundException;
import com.insurance.datacollection.exception.DuplicateCaseNumException;
import com.insurance.datacollection.exception.NoPlanFoundException;
import com.insurance.datacollection.feign.ApplicationRegClient;
import com.insurance.datacollection.feign.PlanCategoryClient;
import com.insurance.datacollection.repo.ChildRepo;
import com.insurance.datacollection.repo.DcCaseRepo;
import com.insurance.datacollection.repo.EducationRepo;
import com.insurance.datacollection.repo.IncomeRepo;
import com.insurance.datacollection.repo.PlanRepo;

import feign.FeignException;
import jakarta.transaction.Transactional;


@Service
public class DataCollectionServiceImpl implements DataCollectionService{
    private ApplicationRegClient registrationClient;
   private PlanCategoryClient categoryClient;
   private PlanRepo planRepo;
   private DcCaseRepo dcCaseRepo;
   private IncomeRepo incomeRepo;
   private EducationRepo educationRepo;
   private ChildRepo childrenRepo;

    public DataCollectionServiceImpl(ApplicationRegClient registrationClient, PlanCategoryClient categoryClient,PlanRepo planRepo,DcCaseRepo dcCaseRepo, IncomeRepo incomeRepo, EducationRepo educationRepo, ChildRepo childrenRepo ) {
        this.registrationClient = registrationClient;
        this.categoryClient=categoryClient;
        this.planRepo=planRepo;
        this.dcCaseRepo=dcCaseRepo;
        this.incomeRepo=incomeRepo;
        this.educationRepo=educationRepo;
        this.childrenRepo=childrenRepo;
    }

    @Override
    public Long appIdValidate(Integer id) throws AppIdErrorException {
        try {
            UserRegisteredSsnResponse registeredSsnResponse = registrationClient.getUserBySsn(id).getBody();
            if (registeredSsnResponse == null) {
                throw new AppIdErrorException("No response from Registration Service.");
            }
//            if(dcCaseRepo.existsByAppId(id)){
//                throw new AppIdErrorException("Entered AppId has Already generated with Case Number");
//            }
           DcCase dcCase = new DcCase();
            dcCase.setAppId(id);
            dcCase.setCaseNumber(generateUnique6DigitId());
            DcCase dc = dcCaseRepo.save(dcCase);
            System.out.println(dc.getCaseNumber());

            return  dc.getCaseNumber();
        } catch (FeignException.NotFound ex) {
            // Handle 404 from Feign call
            throw new AppIdErrorException("Provided Application Id is not registered, please register.");
        } catch (FeignException ex) {
            // Handle other Feign errors (500, 403 etc.)
            throw new AppIdErrorException("Error while calling Registration Service: " + ex.getMessage());
        }


    }

    @Override
    public Map<Long,String> getPlanNames() {
        Map<Long,String> list = categoryClient.getAllCategories().getBody();
        Set<Long> s =list.keySet();
        Map<Long,String> planNames = new HashMap<>();
        for(Long l : s){
            List<PlanResponse>  p = categoryClient.getByCatId(l).getBody();
            for(PlanResponse p1 : p){
                planNames.put(p1.getPlanId(),p1.getPlanName());
            }


        }
        return planNames;
    }


    @Override
    @Transactional
    public Long addPlan(PlanReq request) throws NoPlanFoundException, CaseNumNotFoundException, DuplicateCaseNumException {
        Plan plan = new Plan();
        Map<Long,String> plans = getPlanNames();
        if(planRepo.existsByCaseNumber(request.getCaseNumber())){
            throw new DuplicateCaseNumException("Entered  case Number is already registered with plan Data");
        }
        Long planId = plans.entrySet().stream()
                .filter(p -> p.getValue().equalsIgnoreCase(request.getPlanName())).
                map(i->i.getKey()).findFirst()
                .orElseThrow(() -> new NoPlanFoundException("Plan not found"));
       plan.setPlanName(request.getPlanName());
       DcCase dcCase = dcCaseRepo.findById(request.getCaseNumber()).orElseThrow(()->new NoPlanFoundException("No plan Found"));
       List<DcCase> dcCaseList = dcCaseRepo.findByAppId(dcCase.getAppId());
        for(DcCase li : dcCaseList){
          if(li.getPlanId()!=null && li.getPlanId().equals(planId)){
              throw  new DuplicateCaseNumException("Plan is Already registered with your application id details");
          }
        }
//       if(dcCase.getPlanId()!=null ){
//             throw  new DuplicateCaseNumberException("User with this case number already registered with Plan name ,Try with different Case Number");
//         }

       dcCase.setPlanId(planId);
       dcCaseRepo.save(dcCase);
        plan.setCaseNumber(request.getCaseNumber());
        plan.setPlanName(request.getPlanName());

       planRepo.save(plan);
       return request.getCaseNumber();
    }

    @Override
    public Long saveIncomeData(IncomeReq incomeRequest) throws CaseNumNotFoundException, DuplicateCaseNumException {
        if(!planRepo.existsByCaseNumber(incomeRequest.getCaseNumber())){
            throw new CaseNumNotFoundException("Provided Case Number does Not Exist");
        }
        if(incomeRepo.existsByCaseNumber(incomeRequest.getCaseNumber())){
            throw new DuplicateCaseNumException("Entered  Case Number is already registered with Income Data");
        }
        Income income = new Income();
        BeanUtils.copyProperties(incomeRequest,income);
        incomeRepo.save(income);
        return income.getCaseNumber();
    }

    @Override
    public Long saveEducationData(EducationReq educationRequest) throws CaseNumNotFoundException, DuplicateCaseNumException {
        if(!incomeRepo.existsByCaseNumber(educationRequest.getCaseNumber())){
            throw new CaseNumNotFoundException("Provided Case Number does Not Exist");
        }
        if(educationRepo.existsByCaseNumber(educationRequest.getCaseNumber())){
            throw new DuplicateCaseNumException("Entered  Case Number is already registered with Income Data");
        }
        Education education = new Education();
        BeanUtils.copyProperties(educationRequest,education);
        educationRepo.save(education);
        return education.getCaseNumber();

    }

    @Override
    public Long saveChildrenData(ChildReqWrapper requestWrapper) throws CaseNumNotFoundException, DuplicateCaseNumException {
        if(!educationRepo.existsByCaseNumber(requestWrapper.getCaseNumber())){
            throw new CaseNumNotFoundException("Provided Case Number does Not Exist");
        }
        if(childrenRepo.existsByCaseNumber(requestWrapper.getCaseNumber())){
            throw new DuplicateCaseNumException("Entered  Case Number is already registered with Children Data");
        }

        List<Children> children = new ArrayList<>();

        requestWrapper.getChildrenRequests().stream().forEach(child->{
            Children children1 = new Children();
            children1.setCaseNumber(requestWrapper.getCaseNumber());
            BeanUtils.copyProperties(child,children1);
            children.add(children1);
        });
        childrenRepo.saveAll(children);

        return  requestWrapper.getCaseNumber();
    }

    @Override
    public Summary getSavedData(Long caseNumber) throws CaseNumNotFoundException {
        Summary summary = new Summary();
        DcCase dcCase = dcCaseRepo.findById(caseNumber).orElseThrow(()->new CaseNumNotFoundException("Case Number Not Found"));
        summary.setAppId(dcCase.getAppId());
        Plan plan = planRepo.findByCaseNumber(caseNumber);
        PlanReq planrequest = new PlanReq();
        BeanUtils.copyProperties(plan,planrequest);
        summary.setPlanRequest(planrequest);

        Income income = incomeRepo.findByCaseNumber(caseNumber);
        IncomeReq incomeRequest = new IncomeReq();
        BeanUtils.copyProperties(income,incomeRequest);
        summary.setIncomeRequest(incomeRequest);

        Education education =educationRepo.findByCaseNumber(caseNumber);
        EducationReq educationRequest = new EducationReq();
        BeanUtils.copyProperties(education,educationRequest);
        summary.setEducationRequest(educationRequest);

            List<Children> children   =childrenRepo.findByCaseNumber(caseNumber);
            ChildReqWrapper wrapper  = new ChildReqWrapper();
            List<ChildrenRequest> childrenRequests = children.stream().map(
                    child->{
                        ChildrenRequest req = new ChildrenRequest();
                        BeanUtils.copyProperties(child,req);
                        return req;
                    }
            ).collect(Collectors.toList());
            wrapper.setChildrenRequests(childrenRequests);
            wrapper.setCaseNumber(caseNumber);

            summary.setChildrenRequestWrapper(wrapper);

             return summary;

    }


    public Long generateUnique6DigitId() {
        long id;
        do {
            id = 100000 + new Random().nextInt(900000);
        } while (dcCaseRepo.existsById(id));
        return id;
    }

}