package org.example;

import io.netty.util.internal.ThreadLocalRandom;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class Randomize {


  String rndNameMin = RandomStringUtils.randomAlphabetic(2);
  String rndName = RandomStringUtils.randomAlphabetic(18);
  Boolean rndBool = RandomUtils.nextBoolean();

  String rndPhoneInValid = RandomStringUtils.randomNumeric(1000000,9999999);
  int runPhoneValid = RandomUtils.nextInt(10000000,99999999);

  Instant start = LocalDateTime.of(2023, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC); // Start date
  Instant end = LocalDateTime.of(2025, 12, 31, 23, 59, 59).toInstant(ZoneOffset.UTC); // End date


  //generate random timestamp
  String startStr = "2022-01-01T00:00:00.000+04:00"; // Start timestamp
  String endStr = "2025-12-31T23:59:59.999+04:00"; // End timestamp

  DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
  ZonedDateTime start1 = ZonedDateTime.parse(startStr, formatter);
  ZonedDateTime end1 = ZonedDateTime.parse(endStr, formatter);


  long startMillis = start1.toInstant().toEpochMilli();
  long endMillis = end1.toInstant().toEpochMilli();
  long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis + 1);

  Instant randomInstant = Instant.ofEpochMilli(randomMillis);
  ZonedDateTime randomZonedDateTime = randomInstant.atZone(start1.getZone());

  String randomTimestampStr = randomZonedDateTime.format(formatter);


  String rndWrongNum = RandomStringUtils.randomNumeric(1, 15);

  String rndId = RandomStringUtils.randomAlphanumeric(24);

  String rndEmail = generateRandomEmail();

  private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
  private static final int LENGTH = 10;

  public static String generateRandomEmail() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();

    // generate random characters for the email username
    for (int i = 0; i < LENGTH; i++) {
      char c = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
      sb.append(c);
    }

    // generate a random email domain
    String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "aol.com"};
    String domain = domains[random.nextInt(domains.length)];

    // return the random email
    return sb.toString() + "@" + domain;
  }

  public String getRndNameMin() {
    return rndNameMin;
  }

  public void setRndNameMin(String rndNameMin) {
    this.rndNameMin = rndNameMin;
  }

  public String getRndName() {
    return rndName;
  }

  public void setRndName(String rndName) {
    this.rndName = rndName;
  }

  public Boolean getRndBool() {
    return rndBool;
  }

  public void setRndBool(Boolean rndBool) {
    this.rndBool = rndBool;
  }

  public String getRndEmail() {
    return rndEmail;
  }

  public void setRndEmail(String rndEmail) {
    this.rndEmail = rndEmail;
  }

  public String getRndId() {
    return rndId;
  }

  public void setRndId(String rndId) {
    this.rndId = rndId;
  }

  public String getRndWrongNum() {
    return rndWrongNum;
  }

  public void setRndWrongNum(String rndWrongNum) {
    this.rndWrongNum = rndWrongNum;
  }

  public String getRndPhoneInValid() {
    return rndPhoneInValid;
  }

  public void setRndPhoneInValid(String rndPhoneInValid) {
    this.rndPhoneInValid = rndPhoneInValid;
  }

  public int getRunPhoneValid() {
    return runPhoneValid;
  }

  public void setRunPhoneValid(int runPhoneValid) {
    this.runPhoneValid = runPhoneValid;
  }



  public String getRandomTimestampStr() {
    return randomTimestampStr;
  }

  public void setRandomTimestampStr(String randomTimestampStr) {
    this.randomTimestampStr = randomTimestampStr;
  }
}





