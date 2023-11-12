package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.PageDebit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class TestDebit {
    String approvedCardNumber = DataHelper.getApprovedCard().getCardNumber();
    String declinedCardNumber = DataHelper.getDeclinedCard().getCardNumber();
    String fakeCardNumber = DataHelper.getFakeCardNumber().getCardNumber();
    String validMonth = DataHelper.getValidMonth();
    String validYear = DataHelper.getValidYear(1);
    String validCardOwner = DataHelper.getValidName();
    String validCode = DataHelper.getNumber(3);

    public static String url = System.getProperty("set.url");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void removeAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {

        open(url);
    }

    @AfterEach
    void cleanBase() {

        SqlHelper.cleanBase();
    }

    @Test
    void happyPathCardApproved() {
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void happyPathCardDeclined() {
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(declinedCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForFailNotification();
        assertEquals("DECLINED", SqlHelper.getPaymentStatus());
    }

    @Test
    void fakeCardNumber() {
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(fakeCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForFailNotification();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void emptyCardNumber() {
        var emptyCardNumber = DataHelper.getEmptyValue();
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(emptyCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForNecessaryMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }


    @Test
    void cardNumber15Digits() {
        var cardNumber15Digits = DataHelper.getNumber(15);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(cardNumber15Digits, validMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void cardNumber17Digits() {
        var cardNumber17Digits = "4444 4444 4444 44411";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(cardNumber17Digits, validMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void emptyMonth() {
        var emptyMonth = DataHelper.getEmptyValue();
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, emptyMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForNecessaryMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void zeroMonth() {
        var zeroMonth = "00";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, zeroMonth, validYear, validCardOwner, validCode);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }
    @Test
    void month1Digit() {
        var month1Digit = DataHelper.getNumber(1);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, month1Digit, validYear, validCardOwner, validCode);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }


    @Test
    void month3Digits() {
        var month3Digits = "111";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, month3Digits, validYear, validCardOwner, validCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }
    @Test
    void month13() {
        var month13 = "13";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, month13, validYear, validCardOwner, validCode);
        pageDebit.waitForWrongExpirationMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }
    @Test
    void emptyYear() {
        var emptyYear = DataHelper.getEmptyValue();
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, emptyYear, validCardOwner, validCode);
        pageDebit.waitForNecessaryMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void year1Digit() {
        var year1Digit = DataHelper.getNumber(1);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, year1Digit, validCardOwner, validCode);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void year3Digits() {
        var year3Digits = "255";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, year3Digits, validCardOwner, validCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }
    @Test
    void lastYear() {
        var lastYear = DataHelper.getValidYear(-1);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, lastYear, validCardOwner, validCode);
        pageDebit.waitForCardExpiredMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void overFiveYear() {
        var overFiveYear = DataHelper.getValidYear(6);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, overFiveYear, validCardOwner, validCode);
        pageDebit.waitForWrongExpirationMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void emptyCardOwner() {
        var emptyCardOwner = DataHelper.getEmptyValue();
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, emptyCardOwner, validCode);
        pageDebit.waitForNecessaryMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }


    @Test
    void hyphenCardOwner() {
        var hyphenCardOwner = "Anna-Maria";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, hyphenCardOwner, validCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void cardOwner3characters() {
        var cardOwner3characters = "u li";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, cardOwner3characters, validCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void extraCardOwner() {
        var extraCardOwner = "aaaaaaaaaaaaaaaaaaaa zzzzzzzzzzzzzzzzzzzz";
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, extraCardOwner, validCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void cyrillicCardOwner() {
        var cyrillicCardOwner = DataHelper.getCyrillicName();
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, cyrillicCardOwner, validCode);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void numberCardOwner() {
        var numberCardOwner = DataHelper.getNumber(15);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, numberCardOwner, validCode);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void symbolCardOwner() {
        var symbolCardOwner = DataHelper.getSymbols();
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, symbolCardOwner, validCode);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void emptyCode() {
        var emptyCode = DataHelper.getEmptyValue();
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, emptyCode);
        pageDebit.waitForNecessaryMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void code1Digit() {
        var code1Digit = DataHelper.getNumber(1);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, code1Digit);
        pageDebit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @Test
    void extraCode() {
        var extraCode = DataHelper.getNumber(4);
        var pageDebit = new PageDebit();
        pageDebit.clearFields();
        pageDebit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, extraCode);
        pageDebit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }
}
