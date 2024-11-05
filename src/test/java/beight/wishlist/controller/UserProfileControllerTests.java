package beight.wishlist.controller;

import beight.wishlist.model.UserProfile;
import beight.wishlist.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserProfileController.class)
class UserProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    void frontPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("frontpage"));
    }

    @Test
    void createUser() throws Exception {
        mockMvc.perform(get("/create_user"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_user"));
    }

    @Test
    void saveUser() throws Exception {
        mockMvc.perform(post("/save_user").sessionAttr("userProfile", new UserProfile()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void homePage() throws Exception {
        mockMvc.perform(get("/homepage"))
                .andExpect(status().isOk())
                .andExpect(view().name("frontpage"));
        //TODO undersøg hvordan man laver test af at man er logget ind
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(post("/login").param("username", "test").param("password", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

}
