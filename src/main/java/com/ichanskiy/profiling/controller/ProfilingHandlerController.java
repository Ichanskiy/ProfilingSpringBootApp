package com.ichanskiy.profiling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiling")
public class ProfilingHandlerController {

    public boolean isProfiling = true;

    @GetMapping("/disable")
    private void disableProfiling() {
        isProfiling = false;
    }

    @GetMapping("/enable")
    private void enablePrifiling() {
        isProfiling = true;
    }
}
