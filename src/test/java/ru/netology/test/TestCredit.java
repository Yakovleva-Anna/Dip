package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.PageCredit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class TestCredit {
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
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void happyPathCardDeclined() {
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(declinedCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForFailNotification();
        assertEquals("DECLINED", SqlHelper.getCreditStatus());
    }

    @Test
    void fakeCardNumber() {
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(fakeCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForFailNotification();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void emptyCardNumber() {
        var emptyCardNumber = DataHelper.getEmptyValue();
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(emptyCardNumber, validMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForNecessaryMessage();
        assertNull(SqlHelper.getCreditStatus());
    }


    @Test
    void cardNumber15Digits() {
        var cardNumber15Digits = DataHelper.getNumber(15);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(cardNumber15Digits, validMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void cardNumber17Digits() {
        var cardNumber17Digits = "4444 4444 4444 44411";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(cardNumber17Digits, validMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void emptyMonth() {
        var emptyMonth = DataHelper.getEmptyValue();
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, emptyMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForNecessaryMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void zeroMonth() {
        var zeroMonth = "00";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, zeroMonth, validYear, validCardOwner, validCode);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void month1Digit() {
        var month1Digit = DataHelper.getNumber(1);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, month1Digit, validYear, validCardOwner, validCode);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void month3Digits() {
        var month3Digits = "111";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, month3Digits, validYear, validCardOwner, validCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void month13() {
        var month13 = "13";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, month13, validYear, validCardOwner, validCode);
        pageCredit.waitForWrongExpirationMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void emptyYear() {
        var emptyYear = DataHelper.getEmptyValue();
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, emptyYear, validCardOwner, validCode);
        pageCredit.waitForNecessaryMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void year1Digit() {
        var year1Digit = DataHelper.getNumber(1);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, year1Digit, validCardOwner, validCode);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void year3Digits() {
        var year3Digits = "255";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, year3Digits, validCardOwner, validCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void lastYear() {
        var lastYear = DataHelper.getValidYear(-1);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, lastYear, validCardOwner, validCode);
        pageCredit.waitForCardExpiredMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void overFiveYear() {
        var overFiveYear = DataHelper.getValidYear(6);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, overFiveYear, validCardOwner, validCode);
        pageCredit.waitForWrongExpirationMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void emptyCardOwner() {
        var emptyCardOwner = DataHelper.getEmptyValue();
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, emptyCardOwner, validCode);
        pageCredit.waitForNecessaryMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void hyphenCardOwner() {
        var hyphenCardOwner = "Anna-Maria";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, hyphenCardOwner, validCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void cardOwner3characters() {
        var cardOwner3characters = "u li";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, cardOwner3characters, validCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void extraCardOwner() {
        var extraCardOwner = "aaaaaaaaaaaaaaaaaaaa zzzzzzzzzzzzzzzzzzzz";
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, extraCardOwner, validCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void cyrillicCardOwner() {
        var cyrillicCardOwner = DataHelper.getCyrillicName();
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, cyrillicCardOwner, validCode);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void numberCardOwner() {
        var numberCardOwner = DataHelper.getNumber(15);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, numberCardOwner, validCode);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void symbolCardOwner() {
        var symbolCardOwner = DataHelper.getSymbols();
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, symbolCardOwner, validCode);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void emptyCode() {
        var emptyCode = DataHelper.getEmptyValue();
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, emptyCode);
        pageCredit.waitForNecessaryMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void code1Digit() {
        var code1Digit = DataHelper.getNumber(1);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, code1Digit);
        pageCredit.waitForWrongFormatMessage();
        assertNull(SqlHelper.getCreditStatus());
    }

    @Test
    void extraCode() {
        var extraCode = DataHelper.getNumber(4);
        var pageCredit = new PageCredit();
        pageCredit.clearFields();
        pageCredit.fillForm(approvedCardNumber, validMonth, validYear, validCardOwner, extraCode);
        pageCredit.waitForSuccessNotification();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }
}