package com.ichanskiy.profiling.service;

import com.ichanskiy.profiling.annotation.Profiling;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profiling
public class TestService {


    public void testMethod(){
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i + new Random(1).nextInt(100));
        }
        Collections.sort(integers);
        System.out.println(Arrays.toString(integers.toArray()));
    }
}
