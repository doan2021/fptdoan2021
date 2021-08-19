package com.flearndriving.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.flearndriving.application.common.Constant;
import com.flearndriving.application.dto.AccountForm;
import com.flearndriving.application.dto.AccountUpdateForm;
import com.flearndriving.application.services.AccountServices;
import com.flearndriving.application.services.ChapterServices;
import com.flearndriving.application.services.DrivingLicenseServices;
import com.flearndriving.application.services.ExamQuestionsServices;
import com.flearndriving.application.services.QuestionServices;
import com.flearndriving.application.services.RoleServices;
import com.flearndriving.application.services.TrialExamResultServices;
import com.flearndriving.application.validator.AccountUpdateValidator;
import com.flearndriving.application.validator.AccountValidator;

@Controller
public class AccountsController {

    @Autowired
    ExamQuestionsServices examQuestionsServices;

    @Autowired
    DrivingLicenseServices drivingLicenseServices;

    @Autowired
    TrialExamResultServices trialExamResultServices;

    @Autowired
    AccountServices accountsServices;

    @Autowired
    RoleServices roleServices;

    @Autowired
    private AccountUpdateValidator accountUpdateValidator;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    ChapterServices chapterServices;

    @Autowired
    QuestionServices questionServices;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        if (target.getClass() == AccountForm.class) {
            dataBinder.setValidator(accountValidator);
        }

        if (target.getClass() == AccountUpdateForm.class) {
            dataBinder.setValidator(accountUpdateValidator);
        }
    }

    @GetMapping(value = { "/register" })
    public String registerPage(Model model) {
        model.addAttribute("accountForm", new AccountForm());
        return "register";
    }

    @PostMapping(value = { "/create-account" })
    public String createUser(@ModelAttribute("accountForm") @Validated AccountForm accountForm, BindingResult result,
            Model model) {
        // Validate result
        if (result.hasErrors()) {
            return "register";
        }
        accountsServices.createAccount(accountForm);
        return "register-successful";
    }

    @GetMapping(value = { "/view-profile" })
    public String viewProfile(Model model) {
        model.addAttribute("accountUpdateForm", accountsServices.getAccountLoginInfo());
        return "view-profile";
    }

    @GetMapping(value = { "/view-profile-update" })
    public String viewProfileUpdate(Model model) {
        model.addAttribute("accountUpdateForm", accountsServices.getAccountLoginInfo());
        return "view-profile-update";
    }

    @PostMapping(value = { "/update-account-view" })
    public String updateAccountView(@Validated AccountUpdateForm accountUpdateForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "view-profile-update";
        }
        accountsServices.updateAccount(accountUpdateForm);
        model.addAttribute(Constant.STATUS_SUCCESS, "Cập nhật thông tin thành công!");
        model.addAttribute("accountUpdateForm", accountUpdateForm);
        return "view-profile-update";
    }

    @GetMapping(value = { "/view-profile-registed-exam" })
    public String viewProfileRegistedExam(Model model) {
        model.addAttribute("account", accountsServices.getAccountLoginInfo());
        return "view-profile-registed-exam";
    }

    @GetMapping(value = { "/view-profile-learning-progress" })
    public String viewProfileLearningProgressLong(Model model) {
        model.addAttribute("account", accountsServices.getAccountLoginInfo());
        model.addAttribute("listLearningProgressChapter", chapterServices.learningProgressChapter());
        return "view-profile-learning-progress";
    }

    @GetMapping(value = { "/view-history-trial-test" })
    public String viewHistoryTrialTest(Model model) {
        model.addAttribute("account", accountsServices.getAccountLoginInfo());
        model.addAttribute("listTrialExamResult", trialExamResultServices.findAllTrialExamResult());
        return "view-history-trial-test";
    }

    @GetMapping(value = { "/detail-history-trial-test" })
    public String detailHistoryTrialTest(Long trialExamResultId, Model model) {
        model.addAttribute("trialExamResult", trialExamResultServices.getOne(trialExamResultId));
        return "detail-history-trial-test";
    }
}
