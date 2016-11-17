/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cs4230.finalproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.social.twitter.appId}")
    private String appId;

    @Test
    public void homePageRedirects() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/twitterConnect/twitter"));
    }

    @Test
    public void connectPageHasLink() throws Exception {
        mockMvc.perform(get("/twitterConnect/twitter")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Connect to Twitter")));
    }

    @Test
    public void connectHandlerRedirects() throws Exception {
        if (appId.startsWith("{{")) {
            // User hasn't configured app
            MvcResult result = mockMvc.perform(post("/twitterConnect/twitter"))
                    .andExpect(redirectedUrl("/twitterConnect/twitter")).andReturn();
            assertThat(result.getRequest().getSession()
                    .getAttribute("social_provider_error")).isNotNull();
        }
        else {
            mockMvc.perform(post("/twitterConnect/twitter"))
                    .andExpect(redirectedUrlPattern("https://api.twitter.com/**"));
        }
    }

}