package com.qw73.weatherapptask.util.extensions

import android.location.Location
import com.qw73.weatherapptask.data.model.Coord

fun Location.toCoord(): Coord = Coord(lat = latitude, lon = longitude)