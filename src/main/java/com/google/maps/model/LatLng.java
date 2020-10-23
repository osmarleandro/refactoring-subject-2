/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.google.maps.model;

import com.google.maps.ElevationApi;
import com.google.maps.ElevationApiTest;
import com.google.maps.LocalTestServerContext;
import com.google.maps.internal.StringJoin.UrlValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

import org.junit.Test;

/** A place on Earth, represented by a latitude/longitude pair. */
public class LatLng implements UrlValue, Serializable {

  private static final long serialVersionUID = 1L;

  /** The latitude of this location. */
  public double lat;

  /** The longitude of this location. */
  public double lng;

  /**
   * Constructs a location with a latitude/longitude pair.
   *
   * @param lat The latitude of this location.
   * @param lng The longitude of this location.
   */
  public LatLng(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }

  /** Serialisation constructor. */
  public LatLng() {}

  @Override
  public String toString() {
    return toUrlValue();
  }

  @Override
  public String toUrlValue() {
    // Enforce Locale to English for double to string conversion
    return String.format(Locale.ENGLISH, "%.8f,%.8f", lat, lng);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LatLng latLng = (LatLng) o;
    return Double.compare(latLng.lat, lat) == 0 && Double.compare(latLng.lng, lng) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, lng);
  }

@Test
  public void testGetPath(ElevationApiTest elevationApiTest) throws Exception {
    try (LocalTestServerContext sc =
        new LocalTestServerContext(
            ""
                + "{\n"
                + "   \"results\" : [\n"
                + "      {\n"
                + "         \"elevation\" : 19.11174774169922,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -33.86749,\n"
                + "            \"lng\" : 151.20699\n"
                + "         },\n"
                + "         \"resolution\" : 4.771975994110107\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 456.7416381835938,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -34.32145720949158,\n"
                + "            \"lng\" : 150.5433152252451\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 677.8786010742188,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -34.77180578055915,\n"
                + "            \"lng\" : 149.8724504366625\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 672.6239624023438,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -35.21843425947625,\n"
                + "            \"lng\" : 149.1942540405992\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 1244.74755859375,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -35.66123890186951,\n"
                + "            \"lng\" : 148.5085849619781\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 317.3624572753906,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -36.10011364524662,\n"
                + "            \"lng\" : 147.815302885111\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 797.5011596679688,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -36.53495008485245,\n"
                + "            \"lng\" : 147.1142685138642\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 684.0189819335938,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -36.9656374532439,\n"
                + "            \"lng\" : 146.4053438519865\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 351.05712890625,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -37.39206260399896,\n"
                + "            \"lng\" : 145.6883925043725\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      },\n"
                + "      {\n"
                + "         \"elevation\" : 25.49982643127441,\n"
                + "         \"location\" : {\n"
                + "            \"lat\" : -37.81411,\n"
                + "            \"lng\" : 144.96328\n"
                + "         },\n"
                + "         \"resolution\" : 152.7032318115234\n"
                + "      }\n"
                + "   ],\n"
                + "   \"status\" : \"OK\"\n"
                + "}\n")) {
      ElevationResult[] results = ElevationApi.getByPath(sc.context, 10, ElevationApiTest.SYDNEY, this).await();

      assertNotNull(results);
      assertEquals(10, results.length);
      assertEquals(ElevationApiTest.SYDNEY_ELEVATION, results[0].elevation, ElevationApiTest.EPSILON);
      assertEquals(ElevationApiTest.MELBOURNE_ELEVATION, results[9].elevation, ElevationApiTest.EPSILON);

      sc.assertParamValue("10", "samples");
      sc.assertParamValue("enc:xvumEur{y[jyaWdnbe@", "path");
    }
  }
}
