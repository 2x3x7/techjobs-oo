package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, RedirectAttributes attributes, Errors errors) {
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";
        }

        JobData jobData = JobData.getInstance();

        Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location location = jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

        Job newJob = new Job(jobForm.getName(), employer, location, positionType, coreCompetency);
        jobData.add(newJob);

        attributes.addAttribute("id", newJob.getId());
            return "redirect:/job";


    }

    }



