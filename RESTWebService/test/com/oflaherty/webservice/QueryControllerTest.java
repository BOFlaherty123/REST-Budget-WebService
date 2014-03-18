//package com.oflaherty.webservice.processors;
//
//import org.dom4j.DocumentException;
//import org.junit.Test;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.validation.BindingResult;
//import uk.co.groupama.vehiclelookup.client.helpers.VehicleLookupHelper;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
//
//public class QueryControllerTest {
//
//
//    @Test
//    public void testInitialPageLoad() throws Exception {
//
//        VehicleLookupHelper vehicleLookupHelper = mock(VehicleLookupHelper.class);
//
//        QueryController controller = new QueryController(vehicleLookupHelper);
//
//        MockMvc mockMvc = standaloneSetup(controller).build();
//        mockMvc.perform(get("/query.htm"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("querySearchCriteria"))
//                .andExpect(view().name("query"));
//
//    }
//
//    @Test
//    public void testVehicleQuery() throws Exception {
//
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<vehicle xmlns=\"http://extranet.groupama.co.uk\"><reg_no>A2CAR</reg_no><abi_code>32049201</abi_code><make>MERCEDES</make><model>SL 300 SL</model><body_type>CONVERTIBLE</body_type><gross_weight>0</gross_weight><fuel_type>PETROL</fuel_type><engine_cc>2960</engine_cc><colour>BLACK</colour><doors>2</doors><abi_engine_type>P</abi_engine_type><transmission_type>A</transmission_type><insurance_code>20</insurance_code><group_status/><vin_marker/><vin_no>WDB1290602F034460</vin_no><engine_no>103984220022914</engine_no><make_code>M2</make_code><model_code>226</model_code><make_full/><body_code/><wheel_plan/><co2>0</co2><colour_code>P</colour_code><driver_side>RHD</driver_side><base_model/><variant>AUTO</variant><abi_make>MERCEDES</abi_make><abi_model>300 SL AUTO</abi_model><manufactured_from>1989</manufactured_from><manufactured_to>1993</manufactured_to><series/><abi_engine_cc>2962</abi_engine_cc><abi_body_type>C</abi_body_type><abi_engine_type>P</abi_engine_type><transmission_type>A</transmission_type><security_status/><old_group/><booklet/><code44_mod_flag/><code44_mod_date/><group_mod_flag/><group_mod_date/><code44_key/><create_date/><ncsr_theft_of/><ncsr_theft_from/><manuf_date>19911231</manuf_date><first_reg_date>19910507</first_reg_date><prev_keepers>3</prev_keepers><curr_keeper_date>20071030</curr_keeper_date><prev_keeper_acquire_date/><prev_keeper_disposal_date/><scrap_marker/><scrap_date/><ct_marker>1</ct_marker><export_marker>0</export_marker><export_date/><import_marker>false</import_marker><ni_import_marker>false</ni_import_marker><ni_import_date/><ni_reg_no/><orig_colour/><last_colour/><spare1/><colour_date/><colour_no>0</colour_no><lookupinformation><lookupdate>2014-02-10 04:32:46</lookupdate><lookupid>88ca367bc1ba2c05ac7004a02fdb2fdb</lookupid><source>cache</source></lookupinformation></vehicle>\n";
//
//        VehicleLookupHelper vehicleLookupHelper = mock(VehicleLookupHelper.class);
//        when(vehicleLookupHelper.lookupVehicle(anyString(), anyString(), anyString(), anyInt())).thenReturn(xml);
//
//        QueryController controller = new QueryController(vehicleLookupHelper);
//
//        MockMvc mockMvc = standaloneSetup(controller).build();
//        mockMvc.perform(post("/query.htm")
//                .param("vrn", "a2CAR")
//                .param("cache", "0"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("querySearchCriteria"))
//                .andExpect(model().attributeExists("result"))
//                .andExpect(view().name("query"));
//
//        verify(vehicleLookupHelper).lookupVehicle(
//                "A2CAR", QueryController.USER, QueryController.SYSTEM, 0);
//
//    }
//
//    @Test
//    public void testErrorNoVrn() throws Exception {
//
//        VehicleLookupHelper vehicleLookupHelper = mock(VehicleLookupHelper.class);
//
//        QueryController controller = new QueryController(vehicleLookupHelper);
//
//        MockMvc mockMvc = standaloneSetup(controller).build();
//        MvcResult result = mockMvc.perform(post("/query.htm")
//                .param("vrn", "")
//                .param("cache", "0"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("query")).andReturn();
//
//        BindingResult bindingResult = (BindingResult) result.getModelAndView().getModelMap().get("org.springframework.validation.BindingResult.querySearchCriteria");
//        assertEquals("size must be between 2 and 8", bindingResult.getFieldError("vrn").getDefaultMessage());
//
//    }
//
//    @Test
//    public void testErrorInvalidCache() throws Exception {
//
//        VehicleLookupHelper vehicleLookupHelper = mock(VehicleLookupHelper.class);
//
//        QueryController controller = new QueryController(vehicleLookupHelper);
//
//        MockMvc mockMvc = standaloneSetup(controller).build();
//        MvcResult result = mockMvc.perform(post("/query.htm")
//                .param("vrn", "a2car")
//                .param("cache", "5"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("query")).andReturn();
//
//        BindingResult bindingResult = (BindingResult) result.getModelAndView().getModelMap().get("org.springframework.validation.BindingResult.querySearchCriteria");
//        assertEquals("Must be 0 or 90", bindingResult.getFieldError("cache").getDefaultMessage());
//
//    }
//
//    @Test
//    public void xmlParsing() throws DocumentException {
//
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<vehicle xmlns=\"http://extranet.groupama.co.uk\"><reg_no>A2CAR</reg_no><abi_code>32049201</abi_code><make>MERCEDES</make><model>SL 300 SL</model><body_type>CONVERTIBLE</body_type><gross_weight>0</gross_weight><fuel_type>PETROL</fuel_type><engine_cc>2960</engine_cc><colour>BLACK</colour><doors>2</doors><abi_engine_type>P</abi_engine_type><transmission_type>A</transmission_type><insurance_code>20</insurance_code><group_status/><vin_marker/><vin_no>WDB1290602F034460</vin_no><engine_no>103984220022914</engine_no><make_code>M2</make_code><model_code>226</model_code><make_full/><body_code/><wheel_plan/><co2>0</co2><colour_code>P</colour_code><driver_side>RHD</driver_side><base_model/><variant>AUTO</variant><abi_make>MERCEDES</abi_make><abi_model>300 SL AUTO</abi_model><manufactured_from>1989</manufactured_from><manufactured_to>1993</manufactured_to><series/><abi_engine_cc>2962</abi_engine_cc><abi_body_type>C</abi_body_type><abi_engine_type>P</abi_engine_type><transmission_type>A</transmission_type><security_status/><old_group/><booklet/><code44_mod_flag/><code44_mod_date/><group_mod_flag/><group_mod_date/><code44_key/><create_date/><ncsr_theft_of/><ncsr_theft_from/><manuf_date>19911231</manuf_date><first_reg_date>19910507</first_reg_date><prev_keepers>3</prev_keepers><curr_keeper_date>20071030</curr_keeper_date><prev_keeper_acquire_date/><prev_keeper_disposal_date/><scrap_marker/><scrap_date/><ct_marker>1</ct_marker><export_marker>0</export_marker><export_date/><import_marker>false</import_marker><ni_import_marker>false</ni_import_marker><ni_import_date/><ni_reg_no/><orig_colour/><last_colour/><spare1/><colour_date/><colour_no>0</colour_no><lookupinformation><lookupdate>2014-02-10 04:32:46</lookupdate><lookupid>88ca367bc1ba2c05ac7004a02fdb2fdb</lookupid><source>cache</source></lookupinformation></vehicle>\n";
//
//        QueryController controller = new QueryController(mock(VehicleLookupHelper.class));
//        controller.xmlResultToMap(xml);
//
//
//
//    }
//
//}
