package ua.dp.dryzhyruk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.core.test.mode.TestModeController;

class TestModeControllerTest {

    @Test
    void isTestMode_should_return_true_in_case_init_with_true() {
        //given
        boolean isTestMode = true;
        String testModeRecipientEmail = "ivan.dryzhyruk@dxc.com";
        //when
        TestModeController testModeController = new TestModeController(isTestMode, testModeRecipientEmail);
        boolean actual = testModeController.isTestMode();
        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void isTestMode_should_return_false_in_case_init_with_false() {
        //given
        boolean isTestMode = false;
        String testModeRecipientEmail = "ivan.dryzhyruk@dxc.com";
        //when
        TestModeController testModeController = new TestModeController(isTestMode, testModeRecipientEmail);
        boolean actual = testModeController.isTestMode();
        //then
        Assertions.assertFalse(actual);
    }

    @Test
    void isRecipientInTestMode_should_return_false_all_time_in_case_init_with_isTestMode_false() {
        //given
        boolean isTestMode = false;
        String testModeRecipientEmail = "ivan.dryzhyruk@dxc.com";
        //when
        TestModeController testModeController = new TestModeController(isTestMode, testModeRecipientEmail);
        boolean actualAny = testModeController.isRecipientInTestMode("any");
        boolean actualSpecific = testModeController.isRecipientInTestMode("ivan.dryzhyruk@dxc.com");
        //then
        Assertions.assertFalse(actualAny);
        Assertions.assertFalse(actualSpecific);
    }

    @Test
    void isRecipientInTestMode_should_return_false_for_any_recipient_init_with_isTestMode_true() {
        //given
        boolean isTestMode = true;
        String testModeRecipientEmail = "ivan.dryzhyruk@dxc.com";
        //when
        TestModeController testModeController = new TestModeController(isTestMode, testModeRecipientEmail);
        boolean actualAny = testModeController.isRecipientInTestMode("any");
        //then
        Assertions.assertFalse(actualAny);
    }

    @Test
    void isRecipientInTestMode_should_return_true_for_specific_recipient_init_with_isTestMode_true() {
        //given
        boolean isTestMode = true;
        String testModeRecipientEmail = "ivan.dryzhyruk@dxc.com";
        //when
        TestModeController testModeController = new TestModeController(isTestMode, testModeRecipientEmail);
        boolean actualAny = testModeController.isRecipientInTestMode("ivan.dryzhyruk@dxc.com");
        //then
        Assertions.assertTrue(actualAny);
    }
}