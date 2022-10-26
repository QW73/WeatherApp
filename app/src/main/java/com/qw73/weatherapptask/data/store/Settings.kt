package com.qw73.weatherapptask.data.store

import com.qw73.weatherapptask.data.model.DegreeUnit

interface Settings {
    suspend fun getUnit(): DegreeUnit

    suspend fun setUnit(degreeUnit: DegreeUnit)
}