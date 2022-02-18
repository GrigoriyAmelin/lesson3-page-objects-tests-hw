package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import java.io.File;

import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationFormTests {

    RegistrationPage registrationPage = new RegistrationPage();
    String firstName = "Grisha";
    String lastName = "Amelin";
    String eMail = "qwerty@mail.ru";
    String userNumber = "8999945232";
    String subjects = "English, Commerce, Arts";
    String genderMale = "Male";
    String genderOther = "Other";
    String currentAddress = "Moscow, Kremlin";
    String stateAndCity = "Haryana Panipat";

    @BeforeAll
    static void before() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1088";
    }

    @Test
    void practiceFormTest() {
        registrationPage
                .openPage()
                .setFistNameInput(firstName)
                .setLastNameInput(lastName)
                .setUserEmailInput(eMail);


        //Выбор радио-баттонов и проверка их активности после выбора
        $("[class='custom-control custom-radio custom-control-inline'] #gender-radio-1")
                .shouldNotBe(selected);
        $(byText(genderMale)).click();
        $("[class='custom-control custom-radio custom-control-inline'] #gender-radio-1")
                .shouldBe(selected);
        $(byText(genderOther)).click();
        $("[class='custom-control custom-radio custom-control-inline'] #gender-radio-3")
                .shouldBe(selected);
        $("[class='custom-control custom-radio custom-control-inline'] #gender-radio-1")
                .shouldNotBe(selected);

        registrationPage
                .setUserNumberInput(userNumber)
                .setBirthDate("October", "1937");

        // Проверка выбора предмета из выпадающего списка
        $("#subjectsInput").sendKeys("e");
        $(byText("English")).click();
        $("#subjectsInput").sendKeys("erc");
        $(byText("Commerce")).click();
        $("#subjectsInput").sendKeys("a");
        $(byText("Arts")).click();
        $("#subjectsInput").setValue("erc");

        //Выбор чек-боксов и проверка их активности после выбора
        $(byText("Reading")).click();
        $("#hobbies-checkbox-1").parent().click();
        $("[class='custom-control custom-checkbox custom-control-inline'] #hobbies-checkbox-1")
                .shouldBe(selected);
        $("[class='custom-control custom-checkbox custom-control-inline'] #hobbies-checkbox-3")
                .shouldNotBe(selected);
        $("#hobbies-checkbox-3").scrollTo().parent().click();
        $("[class='custom-control custom-checkbox custom-control-inline'] #hobbies-checkbox-3")
                .shouldBe(selected);

        $("#uploadPicture").uploadFile(new File("src/test/resources/img/11.jpg"));
        File myfile = new File("src/test/resources/img/12.jpg");
        $("#uploadPicture").uploadFile(myfile);

        $("#currentAddress").setValue(currentAddress);
        $("#state").click();
        $(byText("Haryana")).click();
        $("#city").click();
        $(byText("Panipat")).click();
        $("#submit").click();

        // Проверки финальной формы
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(
                text(firstName + " " + lastName),
                text(eMail),
                text(genderOther),
                text(userNumber),
                text("02 October,1937"),
                text(subjects),
                text("Reading, Sports, Music"),
                text("12.jpg"),
                text(currentAddress),
                text(stateAndCity)
        );

        registrationPage
                .checkForm("Student Name", firstName + " " + lastName)
                .checkForm("Student Email", eMail)
                .checkForm("Gender", genderOther)
                .checkForm("Mobile", userNumber)
                .checkForm("Date of Birth", "02 October,1937")
                .checkForm("Subjects", subjects)
                .checkForm("Hobbies", "Reading, Sports, Music")
                .checkForm("Picture", "12.jpg")
                .checkForm("Address", currentAddress)
                .checkForm("State and City", stateAndCity);

        System.out.println("итоговый тест прошел!");
    }

}