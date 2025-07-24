package com.example.restaurant.management.service;

import com.example.restaurant.management.entity.Revenue;
import com.example.restaurant.management.repository.RevenueRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RevenueService {

    private final RevenueRepo revenueRepo;

    public Revenue getRevenueByDay(LocalDate date) {
        return revenueRepo.findByDate(date);
    }

    public List<Revenue> getRevenueByWeek(LocalDate referenceDate) {
        LocalDate startOfWeek = referenceDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = referenceDate.with(DayOfWeek.SUNDAY);
        return revenueRepo.findByDateBetween(startOfWeek, endOfWeek);
    }

    public List<Revenue> getRevenueByMonth(YearMonth yearMonth) {
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();
        return revenueRepo.findByDateBetween(start, end);
    }
}