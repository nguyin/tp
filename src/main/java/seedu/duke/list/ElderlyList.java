package seedu.duke.list;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.duke.common.Elderly;
import seedu.duke.common.HighRiskElderly;
import seedu.duke.common.JsonDeserializerWithInheritance;
import seedu.duke.common.LowRiskElderly;
import seedu.duke.common.Medicine;
import seedu.duke.common.Appointment;
import seedu.duke.common.MediumRiskElderly;
import seedu.duke.common.NextOfKin;
import seedu.duke.common.Record;

import seedu.duke.exceptions.DoctorIndexException;
import seedu.duke.exceptions.DukeException;
import seedu.duke.exceptions.DuplicateElderlyException;
import seedu.duke.exceptions.ElderlyNotFoundException;
import seedu.duke.exceptions.HospitalIndexException;
import seedu.duke.exceptions.InvalidAddMedicineFormatException;
import seedu.duke.exceptions.InvalidAddNokFormatException;
import seedu.duke.exceptions.InvalidAddRecordFormatException;
import seedu.duke.exceptions.InvalidDeleteApptFormatException;
import seedu.duke.exceptions.InvalidDeleteElderlyException;
import seedu.duke.exceptions.InvalidDeleteMedFormatException;
import seedu.duke.exceptions.InvalidDeleteNokFormatException;
import seedu.duke.exceptions.InvalidElderlyFormatException;
import seedu.duke.exceptions.InvalidLoadFromFilePathException;
import seedu.duke.exceptions.InvalidSetBloodPressureException;
import seedu.duke.exceptions.InvalidViewMedicineFormatException;
import seedu.duke.exceptions.InvalidAddAppointmentFormatException;
import seedu.duke.exceptions.InvalidViewAppointmentFormatException;
import seedu.duke.exceptions.InvalidStoreToFilePathException;
import seedu.duke.exceptions.InvalidViewByNameException;
import seedu.duke.exceptions.InvalidViewDietException;
import seedu.duke.exceptions.InvalidViewMedicineException;
import seedu.duke.exceptions.InvalidViewNokFormatException;
import seedu.duke.exceptions.InvalidViewRecordFormatException;
import seedu.duke.exceptions.InvalidRiskLevelException;
import seedu.duke.exceptions.InvalidInputException;
import seedu.duke.hospital.Doctor;
import seedu.duke.hospital.Hospital;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;
import java.util.Optional;

import com.google.gson.JsonSyntaxException;

import static seedu.duke.common.MagicValues.SPLIT_ADD_NOK;
import static seedu.duke.common.MagicValues.SPLIT_ADD_RECORD;
import static seedu.duke.common.MagicValues.SPLIT_DELETE_APPOINTMENT;
import static seedu.duke.common.MagicValues.SPLIT_DELETE_NOK;
import static seedu.duke.common.MagicValues.SPLIT_DELETE_MED;
import static seedu.duke.common.MagicValues.HIGH;
import static seedu.duke.common.MagicValues.INDEX_OF_DELETE_DATE;
import static seedu.duke.common.MagicValues.INDEX_OF_DELETE_TIME;
import static seedu.duke.common.MagicValues.INDEX_OF_ELDERLY_USERNAME;
import static seedu.duke.common.MagicValues.INDEX_OF_FILE_PATH;
import static seedu.duke.common.MagicValues.INPUT_OFFSET;
import static seedu.duke.common.MagicValues.SPLIT_LOAD_FILE;
import static seedu.duke.common.MagicValues.LOW;
import static seedu.duke.common.MagicValues.MEDIUM;
import static seedu.duke.common.MagicValues.SPLIT_NAME;
import static seedu.duke.common.MagicValues.SPLIT_ADD_MEDICINE;
import static seedu.duke.common.MagicValues.SPLIT_ADD_APPOINTMENT;
import static seedu.duke.common.MagicValues.INDEX_OF_DATE;
import static seedu.duke.common.MagicValues.INDEX_OF_ELDERLY_NAME;
import static seedu.duke.common.MagicValues.INDEX_OF_RISK_LEVEL;
import static seedu.duke.common.MagicValues.INDEX_OF_ELDERLY_ADDRESS;
import static seedu.duke.common.MagicValues.INDEX_OF_FREQUENCY;
import static seedu.duke.common.MagicValues.INDEX_OF_LOCATION;
import static seedu.duke.common.MagicValues.INDEX_OF_ELDERLY_PHONE_NUMBER;
import static seedu.duke.common.MagicValues.INDEX_OF_MEDICINE_NAME;
import static seedu.duke.common.MagicValues.INDEX_OF_NOK_ADDRESS;
import static seedu.duke.common.MagicValues.INDEX_OF_NOK_EMAIL;
import static seedu.duke.common.MagicValues.INDEX_OF_NOK_PHONE_NUMBER;
import static seedu.duke.common.MagicValues.INDEX_OF_NOK_RELATIONSHIP;
import static seedu.duke.common.MagicValues.INDEX_OF_PURPOSE;
import static seedu.duke.common.MagicValues.INDEX_OF_TIME;
import static seedu.duke.common.MagicValues.LENGTH_IF_PURPOSE_EXISTS;
import static seedu.duke.common.MagicValues.INDEX_OF_NOK_NAME;
import static seedu.duke.common.MagicValues.INDEX_OF_BIRTHDAY;
import static seedu.duke.common.MagicValues.INDEX_OF_SYSTOLIC_PRESSURE;
import static seedu.duke.common.MagicValues.INDEX_OF_DIASTOLIC_PRESSURE;
import static seedu.duke.common.MagicValues.GENERAL_CHECKUP;
import static seedu.duke.common.MagicValues.INDEX_OF_SYSTOLIC_PRESSURE_IN_ARRAY;
import static seedu.duke.common.MagicValues.INDEX_OF_DIASTOLIC_PRESSURE_IN_ARRAY;
import static seedu.duke.common.MagicValues.SIMILARITY_INDEX;
import static seedu.duke.common.MagicValues.SPLIT_STORE_FILE;
import static seedu.duke.common.MagicValues.VALUE_DEFAULT_BP;
import static seedu.duke.common.MagicValues.parser;
import static seedu.duke.common.MagicValues.ui;
import static seedu.duke.common.MagicValues.re;
import static seedu.duke.common.MagicValues.hospitalArrayList;


import static seedu.duke.common.Messages.MESSAGE_NO_BLOOD_PRESSURE;
import static seedu.duke.common.Messages.MESSAGE_NUMBER_OF_ELDERLY;
import static seedu.duke.common.Messages.MESSAGE_SET_BLOOD_PRESSURE;


public class ElderlyList {


    protected static ArrayList<Elderly> elderlyArrayList = new ArrayList<>();
    protected static HashMap<String, HashSet<String>> medicineMappings = new HashMap<>();
    protected static HashMap<String, HashSet<String>> dietMappings = new HashMap<>();

    private String filePath;
    private final Gson gson;

    public ElderlyList(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Elderly.class, new JsonDeserializerWithInheritance<Elderly>()).create();
    }

    /**
     * Returns the elderly array list.
     *
     * @return elderlyArrayList is the elderly array list.
     */
    public ArrayList<Elderly> getElderlyArrayList() {
        return elderlyArrayList;
    }

    /**
     * Checks in current list if current elderly username exists.
     * @param inputElderlyName String containing elderly username to check with
     * @return Boolean value if elderly exists in array list
     */
    private boolean elderlyExists(String inputElderlyName) {
        return elderlyArrayList
                .stream()
                .anyMatch(s -> s.getUsername().equals(inputElderlyName));
    }

    /**
     * Adds the elderly into the elderly array list, together with risk level and additional info if required.
     *
     * @param userLine Line that is inputted by the user.
     */
    public void addElderly(String userLine) {
        try {
            if (!re.isValidAddElderly(userLine)) {
                throw new InvalidElderlyFormatException();
            }
            String[] paramList = userLine.split(SPLIT_NAME);
            String userName = paramList[INDEX_OF_ELDERLY_USERNAME];
            String elderlyName = paramList[INDEX_OF_ELDERLY_NAME];
            String riskLevel = paramList[INDEX_OF_RISK_LEVEL].toUpperCase();
            if (!re.isValidRiskLevel(riskLevel)) {
                throw new InvalidRiskLevelException();
            }
            // If Elderly username exists, reject addition
            if (elderlyExists(userName)) {
                throw new DuplicateElderlyException();
            }
            switch (riskLevel) {
            case LOW:
                addLowRiskElderly(userName, elderlyName);
                break;
            case MEDIUM:
                addMediumRiskElderly(userName, elderlyName);
                break;
            case HIGH:
                addHighRiskElderly(userName, elderlyName);
                break;
            default:
                break;
            }
            ui.printAddElderlyMessage();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Adds the elderly as a low risk elderly.
     *
     * @param userName username of elderly.
     * @param elderlyName name of elderly.
     */
    private void addLowRiskElderly(String userName, String elderlyName) {
        elderlyArrayList.add(new LowRiskElderly(userName, elderlyName));
    }

    /**
     * Adds the elderly as a medium risk elderly.
     *
     * @param userName username of elderly.
     * @param elderlyName name of elderly.
     */
    private void addMediumRiskElderly(String userName, String elderlyName) throws HospitalIndexException {
        hospitalArrayList.printHospitalNames();
        ui.printEnterHospitalMessage();
        String stringHospitalIndex = ui.getUserInput();
        if (!re.isValidHospitalIndex(stringHospitalIndex)) {
            throw new HospitalIndexException();
        }
        int hospitalIndex = Integer.parseInt(stringHospitalIndex) - INPUT_OFFSET;
        Hospital hospital = hospitalArrayList.getHospital(hospitalIndex);
        ui.printEnterConditionsMessage();
        String conditions = ui.getUserInput();
        ui.printEnterNotesOnCareMessage();
        String notesOnCare = ui.getUserInput();
        elderlyArrayList.add(new MediumRiskElderly(userName, elderlyName, hospital, conditions, notesOnCare));
    }

    /**
     * Adds the elderly as a high risk elderly.
     *
     * @param userName username of elderly.
     * @param elderlyName name of elderly.
     */
    private void addHighRiskElderly(String userName, String elderlyName) throws HospitalIndexException,
            DoctorIndexException {
        hospitalArrayList.printHospitalNames();
        ui.printEnterHospitalMessage();
        String stringHospitalIndex = ui.getUserInput();
        if (!re.isValidHospitalIndex(stringHospitalIndex)) {
            throw new HospitalIndexException();
        }
        int hospitalIndex = Integer.parseInt(stringHospitalIndex) - INPUT_OFFSET;
        Hospital hospital = hospitalArrayList.getHospital(hospitalIndex);
        ui.printEnterConditionsMessage();
        final String conditions = ui.getUserInput();
        ui.printEnterNotesOnCareMessage();
        final String notesOnCare = ui.getUserInput();
        hospital.printDoctorNames();
        ui.printEnterDoctorMessage();
        String stringDoctorIndex = ui.getUserInput();
        if (!re.isValidDoctorIndex(stringDoctorIndex)) {
            throw new DoctorIndexException();
        }
        Doctor doctor = hospital.getDoctor(Integer.parseInt(stringDoctorIndex) - INPUT_OFFSET);
        elderlyArrayList.add(new HighRiskElderly(userName, elderlyName, hospital,
                conditions, notesOnCare, doctor));
    }

    /**
     * Adds the medicine to the given elderly based on the input.
     *
     * @param userLine Line that is inputted by the user.
     */
    public void addMedicine(String userLine) {
        try {
            if (!re.isValidAddMedicine(userLine)) {
                throw new InvalidAddMedicineFormatException();
            }
            String[] paramList = userLine.split(SPLIT_ADD_MEDICINE);
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            String medicineName = paramList[INDEX_OF_MEDICINE_NAME];
            String frequency = paramList[INDEX_OF_FREQUENCY];
            assert paramList.length == 4 : "addmed input does not have all required values";
            elderly.addMedicine(new Medicine(medicineName, frequency));
            ui.printAddMedicineMessage();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Views all medicine the elderly needs to take based on the input.
     *
     * @param userLine Line that is inputted by the user.
     */
    public void viewMedicine(String userLine) {
        try {
            if (!re.isValidViewMedicine(userLine)) {
                throw new InvalidViewMedicineFormatException();
            }
            String[] paramList = userLine.split(SPLIT_NAME);
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            assert paramList.length == 2 : "Username is empty";
            Elderly elderly = getElderly(elderlyName);
            printMedicines(elderly);
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Prints all the medicine, given the elderly.
     *
     * @param elderly The elderly in question.
     */
    private void printMedicines(Elderly elderly) {
        ArrayList<Medicine> medicineList = elderly.getMedicines();
        System.out.println("Medicine of " + elderly.getUsername() + " are shown below:");
        for (int index = 1; index <= medicineList.size(); ++index) {
            System.out.format("% 3d.", index);
            System.out.println(medicineList.get(index - 1));
        }
    }

    /**
     * Adds the appointment to the given elderly based on the input.
     *
     * @param userLine Line that is inputted by the user.
     */
    public void addAppointment(String userLine) {
        try {
            if (!re.isValidAddAppointment(userLine)) {
                throw new InvalidAddAppointmentFormatException();
            }
            String[] paramList = userLine.split(SPLIT_ADD_APPOINTMENT);
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            String location = paramList[INDEX_OF_LOCATION];
            String date = paramList[INDEX_OF_DATE];
            String time = paramList[INDEX_OF_TIME];
            String purpose;
            if (paramList.length == LENGTH_IF_PURPOSE_EXISTS) {
                purpose = paramList[INDEX_OF_PURPOSE];
            } else {
                purpose = GENERAL_CHECKUP;
            }
            assert paramList.length == 5 || paramList.length == 6 : "addappt input does not have all required values";
            elderly.addAppointment(new Appointment(location, date, time, purpose));
            ui.printAddAppointmentMessage();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Views all appointments the elderly has based on the input.
     *
     * @param userLine Line that is inputted by the user.
     */
    public void viewAppointment(String userLine) {
        try {
            if (!re.isValidViewAppointment(userLine)) {
                throw new InvalidViewAppointmentFormatException();
            }
            String[] paramList = userLine.split(SPLIT_NAME);
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            assert paramList.length == 2 : "Username is empty";
            Elderly elderly = getElderly(elderlyName);
            printAppointments(elderly);
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    public void deleteAppointment(String userLine) {
        try {
            if (!re.isValidDeleteAppointment(userLine)) {
                throw new InvalidDeleteApptFormatException();
            }
            String[] paramList = userLine.split(SPLIT_DELETE_APPOINTMENT);
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            String date = paramList[INDEX_OF_DELETE_DATE];
            String time = paramList[INDEX_OF_DELETE_TIME];
            Elderly elderly = getElderly(elderlyName);
            Optional<Appointment> deletedAppointment = elderly.removeAppointment(date, time);
            deletedAppointment.ifPresentOrElse(this::printDeletedAppointment, this::printNoAppointment);
            System.gc();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    public void printDeletedAppointment(Appointment deletedAppointment) {
        System.out.println("These appointment details are now deleted:");
        System.out.println(deletedAppointment);
    }

    public void printNoAppointment() {
        System.out.println("No Appointment found");
    }

    /**
     * Prints all the appointments, given the elderly.
     *
     * @param elderly The elderly in question.
     */
    private void printAppointments(Elderly elderly) {
        int counter = 1;
        System.out.println("Appointments of " + elderly.getUsername() + " are shown below:");
        for (Appointment appointment : elderly.getAppointments()) {
            System.out.format("% 3d.", counter);
            System.out.println(appointment);
            counter++;
        }
    }

    /**
     * Adds Next-Of-Kin information to the given elderly based on the input.
     *
     * @param userLine Line that is inputted by user.
     */
    public void addNok(String userLine) {
        try {
            if (!re.isValidAddNok(userLine)) {
                throw new InvalidAddNokFormatException();
            }
            String[] paramList = userLine.split(SPLIT_ADD_NOK);
            assert paramList.length == 7 : "addnok input does not have all required values";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            String nokName = paramList[INDEX_OF_NOK_NAME];
            String nokPhoneNumber = paramList[INDEX_OF_NOK_PHONE_NUMBER];
            String nokEmail = paramList[INDEX_OF_NOK_EMAIL];
            String nokAddress = paramList[INDEX_OF_NOK_ADDRESS];
            String nokRelationship = paramList[INDEX_OF_NOK_RELATIONSHIP];
            elderly.addNok(new NextOfKin(nokName, nokPhoneNumber, nokEmail, nokAddress, nokRelationship));
            ui.printAddNokMessage();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }


    /**
     * Views the Next-Of_kin information of the elderly based on input.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void viewNok(String userLine) {
        try {
            if (!re.isValidViewNok(userLine)) {
                throw new InvalidViewNokFormatException();
            }
            String[] paramList = userLine.split(SPLIT_NAME);
            assert paramList.length == 2 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            printNextOfKin(elderly);
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Prints the Next-Of-Kin information, given the elderly.
     *
     * @param elderly The elderly in question.
     */
    public void printNextOfKin(Elderly elderly) {
        System.out.println("Details of " + elderly.getUsername() + " Next-of-Kin are shown below:");
        System.out.println(elderly.getNextOfKin());
    }

    public void deleteNextOfKin(String userLine) {
        try {
            if (!re.isValidDeleteNok(userLine)) {
                throw new InvalidDeleteNokFormatException();
            }
            String[] paramList = userLine.split(SPLIT_DELETE_NOK);
            assert paramList.length == 3 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            String nokName = paramList[INDEX_OF_NOK_NAME];
            Elderly elderly = getElderly(elderlyName);
            Optional<NextOfKin> deletedNok = elderly.removeNok(nokName);
            deletedNok.ifPresentOrElse(this::printDeletedNextOfKin, this::printNoNextOfKin);
            System.gc();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }


    public void printDeletedNextOfKin(NextOfKin deletedNok) {
        System.out.println("These details are now deleted:");
        System.out.println(deletedNok);
    }

    public void printNoNextOfKin() {
        System.out.println("No Next-Of-Kin found");
    }

    public void deleteMedicine(String userLine) {
        try {
            if (!re.isValidDeleteMedicine(userLine)) {
                throw new InvalidDeleteMedFormatException();
            }
            String[] paramList = userLine.split(SPLIT_DELETE_MED);
            assert paramList.length == 3 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            String medName = paramList[INDEX_OF_MEDICINE_NAME];
            Elderly elderly = getElderly(elderlyName);
            Optional<Medicine> deletedMed = elderly.removeMedicine(medName);
            deletedMed.ifPresentOrElse(this::printDeletedMedicine, this::printNoMedicine);
            System.gc();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    public void printNoMedicine() {
        System.out.println("No Medicine found");
    }

    public void printDeletedMedicine(Medicine deletedMed) {
        System.out.println("These medicine details are now deleted:");
        System.out.println(deletedMed);
    }

    /**
     * Adds information to the given elderly based on the input.
     *
     * @param userLine Line that is inputted by user.
     */
    public void addRecord(String userLine) {
        try {
            if (!re.isValidAddRecord(userLine)) {
                throw new InvalidAddRecordFormatException();
            }
            String[] paramList = userLine.split(SPLIT_ADD_RECORD);
            assert paramList.length == 4 : "addrec input does not have all required values";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            String elderlyPhoneNumber = paramList[INDEX_OF_ELDERLY_PHONE_NUMBER];
            String elderlyAddress = paramList[INDEX_OF_ELDERLY_ADDRESS];
            elderly.addRecord(new Record(elderlyPhoneNumber, elderlyAddress));
            ui.printAddRecordMessage();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Views the information of the elderly based on input.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void viewRecord(String userLine) {
        try {
            if (!re.isValidViewRec(userLine)) {
                throw new InvalidViewRecordFormatException();
            }
            String[] paramList = userLine.split(SPLIT_NAME);
            assert paramList.length == 2 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            printRecord(elderly);
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Prints the information, given the elderly.
     *
     * @param elderly The elderly in question.
     */
    public void printRecord(Elderly elderly) {
        System.out.println("Record of " + elderly.getUsername() + " is shown below:");
        System.out.println(elderly.getRecord());
    }

    /**
     * Gets the Elderly object given the username.
     *
     * @param username String containing name of elderly.
     * @return Elderly object.
     */
    public Elderly getElderly(String username) throws ElderlyNotFoundException {
        int counter = 0;
        boolean elderlyExists = false;
        for (Elderly elderly : elderlyArrayList) {
            if (Objects.equals(elderly.getUsername(), username)) {
                elderlyExists = true;
                break;
            }
            counter++;
        }
        if (!elderlyExists) {
            throw new ElderlyNotFoundException();
        }
        //assert counter < getElderlyCount() : "Elderly is not found";
        return elderlyArrayList.get(counter);
    }

    /**
     * Views the blood pressure of the elderly.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void viewBloodPressure(String userLine) {
        try {
            String[] paramList = userLine.split(" u/");
            assert paramList.length == 2 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            printBloodPressure(elderly);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    private void printBloodPressure(Elderly elderly) {
        Integer[] bloodPressure = elderly.getBloodPressure();
        if (!elderly.isBloodPressureSet()) {
            System.out.printf(MESSAGE_NO_BLOOD_PRESSURE, elderly.getName());
        } else {
            System.out.printf("Blood pressure of %s is now (%d %d)%n", elderly.getName(),
                    bloodPressure[INDEX_OF_SYSTOLIC_PRESSURE_IN_ARRAY],
                    bloodPressure[INDEX_OF_DIASTOLIC_PRESSURE_IN_ARRAY]);
        }
    }

    /**
     * Sets the blood pressure of the elderly.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void setBloodPressure(String userLine) {
        try {
            if (!re.isValidSetBloodPressure(userLine)) {
                throw new InvalidSetBloodPressureException();
            }
            String[] paramList = userLine.split(" [usd]/");
            assert paramList.length == 4 : "setbloodpressure input does not have all required values";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Integer systolicPressure = Integer.parseInt(paramList[INDEX_OF_SYSTOLIC_PRESSURE]);
            Integer diastolicPressure = Integer.parseInt(paramList[INDEX_OF_DIASTOLIC_PRESSURE]);
            Elderly elderly;
            elderly = getElderly(elderlyName);
            elderly.setBloodPressure(systolicPressure, diastolicPressure);
            ui.printSetBloodPressureMessage(elderly);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Views the birthday of the elderly.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void viewBirthday(String userLine) {
        try {
            String[] paramList = userLine.split(" u/");
            assert paramList.length == 2 : "Name is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            printBirthday(elderly);
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    private void printBirthday(Elderly elderly) {
        System.out.printf("Birthday of %s is %s%n", elderly.getUsername(), elderly.getBirthday());
    }

    /**
     * Sets the birthday of the elderly.
     *
     * @param userLine Line that has been inputted by user.
     */
    public Optional<Elderly> setBirthday(String userLine) {
        String[] paramList = userLine.split(" [ub]/");
        assert paramList.length == 3 : "setbirthday input does not have all required values";
        String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
        String birthday = paramList[INDEX_OF_BIRTHDAY];
        Elderly elderly;
        try {
            elderly = getElderly(elderlyName);
            elderly.setElderlyBirthday(birthday);
            return Optional.of(elderly);
        } catch (ElderlyNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Sets the vaccination status of the elderly to be true.
     *
     * @param userLine Line that has been inputted by user.
     */
    public Optional<Elderly> setVaccinated(String userLine) {
        String[] paramList = userLine.split(" u/");
        assert paramList.length == 2 : "setvaccinated input does not have all required values";
        String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
        Elderly elderly;
        try {
            elderly = getElderly(elderlyName);
            elderly.setVaccinated();
            return Optional.of(elderly);
        } catch (ElderlyNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets the vaccination status of the elderly.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void getVaccinationStatus(String userLine) {
        try {
            String[] paramList = userLine.split(" u/");
            assert paramList.length == 2 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            elderly.printVaccinationStatus();
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Sets the dietary preference of the elderly according to user's selection.
     *
     * @param userLine Line that has been inputted by user.
     */
    public Optional<Elderly> setDietaryPreference(String userLine) {
        String[] paramList = userLine.split(" u/");
        assert paramList.length == 2 : "setdiet input does not have all required values";
        String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
        Elderly elderly;
        try {
            elderly = getElderly(elderlyName);
            elderly.setDietByUserChoice();
            return Optional.of(elderly);
        } catch (ElderlyNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets the dietary preference of the elderly.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void viewDietaryPreference(String userLine) {
        try {
            String[] paramList = userLine.split(" u/");
            assert paramList.length == 2 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            elderly.printDietaryPreference();
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Add medical history of the elderly according to user's selection.
     *
     * @param userLine Line that has been inputted by user.
     */
    public Optional<Elderly> addMedicalHistory(String userLine) {
        String[] paramList = userLine.split(" u/");
        assert paramList.length == 2 : "addmedicalhistory input does not have all required values";
        String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
        Elderly elderly;
        try {
            elderly = getElderly(elderlyName);
            elderly.setMedicalHistory();
            return Optional.of(elderly);
        } catch (ElderlyNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Prints the dietary preference of the elderly.
     *
     * @param userLine Line that has been inputted by user.
     */
    public void viewMedicalHistory(String userLine) {
        try {
            String[] paramList = userLine.split(" u/");
            assert paramList.length == 2 : "Username is empty";
            String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
            Elderly elderly = getElderly(elderlyName);
            elderly.printMedicalHistory();
        } catch (DukeException e) {
            ui.printDukeException(e);
        }
    }

    /**
     * Delete medical history of the elderly according to user's selection.
     *
     * @param userLine Line that has been inputted by user.
     */
    public Optional<Elderly> deleteMedicalHistory(String userLine) {
        String[] paramList = userLine.split(" u/");
        assert paramList.length == 2 : "deletemedicalhistor input does not have all required values";
        String elderlyName = paramList[INDEX_OF_ELDERLY_USERNAME];
        Elderly elderly;
        Elderly returningElderly;
        try {
            elderly = getElderly(elderlyName);
            returningElderly = elderly.deleteMedicalHistory();
            return Optional.of(returningElderly);
        } catch (ElderlyNotFoundException e) {
            return Optional.empty();
        }
    }


    /**
     * Get the number of elderly in the current list.
     */
    public int getElderlyCount() {
        return elderlyArrayList.size();
    }

    /**
     * Returns a consolidated String of all the elderly.
     *
     * @return String containing all the elderly information in the system.
     */
    public String getConsolidatedStringOfElderly() {
        return elderlyArrayList
                .stream()
                .map(Objects::toString)
                .reduce((t, u) -> t + '\n' + u)
                .orElse("");
    }

    /**
     * Prints current list of elderly along with number of elderly in system.
     */
    public void printElderly() {
        System.out.printf(MESSAGE_NUMBER_OF_ELDERLY, getElderlyCount());
        ui.printElderlyList(getConsolidatedStringOfElderly());
    }

    /**
     * Builds the different mappings between medicine name, diet preference and names of elderly.
     */
    public void updateMappings() {
        // Creation of hashmap to do mapping between medicine and list of elderly that take given medicine
        for (Elderly elderlyObject : elderlyArrayList) {
            String elderlyName = elderlyObject.getUsername();
            // Update Medicine Mappings
            for (Medicine medicineObject : elderlyObject.getMedicines()) {
                String medicineName = medicineObject.getMedicineName();
                // Create new Medicine name to Elderly name HashSet Mapping if it doesn't exist
                if (!medicineMappings.containsKey(medicineName)) {
                    medicineMappings.put(medicineName, new HashSet<>());
                }
                // Append Elderly name to list of medicine mapping
                medicineMappings.get(medicineName).add(elderlyName);
            }
            // Update diet mappings
            String dietPreference = elderlyObject.getDiet();
            if (!dietMappings.containsKey(dietPreference)) {
                dietMappings.put(dietPreference, new HashSet<>());
            }
            dietMappings.get(dietPreference).add(elderlyName);
        }
    }

    /**
     * Returns a consolidated String of all elderly taking a given medicine.
     *
     * @param medicineQuery String containing medicine to be looked up.
     * @return String containing all the elderly names in the system that takes the medicine.
     */
    public String buildElderlyStringGivenMedicine(String medicineQuery) {
        String resultString = "";
        updateMappings();

        // Query HashSet for medicine name
        if (medicineMappings.containsKey(medicineQuery)) {
            // Gets all elderly names as a string
            resultString = String.join(System.lineSeparator(), medicineMappings.get(medicineQuery));
        }
        return resultString;
    }

    /**
     * Prints the list of elderly that is taking the searched medicine.
     *
     * @param userLine String containing medicine to be looked up.
     */
    public void getElderlyGivenMedicine(String userLine) {
        try {
            // Check if format is correct
            if (!re.isValidFindMed(userLine)) {
                throw new InvalidViewMedicineException();
            }

            String medicineQuery = parser.getMedicineFromSearchMed(userLine);
            ui.printQueryResultsIntroString(medicineQuery);
            String results = buildElderlyStringGivenMedicine(medicineQuery);
            if (results.isEmpty()) {
                checkSimilarities(medicineMappings.keySet(), medicineQuery);
            } else {
                System.out.println(results);
            }
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        }
    }

    /**
     * Returns a consolidated String of all elderly with a certain diet.
     *
     * @param dietQuery String containing diet preference to be looked up.
     * @return String containing all the elderly names in the system that has given diet preference.
     */
    public String buildElderlyStringGivenDiet(String dietQuery) {
        String resultString = "";
        updateMappings();

        // Query HashSet for diet name
        if (dietMappings.containsKey(dietQuery)) {
            // Gets all elderly names as a string
            resultString = String.join(System.lineSeparator(), dietMappings.get(dietQuery));
        }
        return resultString;
    }

    /**
     * Prints the list of elderly that is taking the searched diet preference.
     * TODO: Unable to test this function without fix to diet
     *
     * @param userLine String containing diet to be looked up.
     */
    public void getElderlyGivenDiet(String userLine) {
        try {
            // Check if format is correct
            if (!re.isValidFindDiet(userLine)) {
                throw new InvalidViewDietException();
            }

            String dietQuery = parser.getDietFromSearchMed(userLine);
            ui.printQueryResultsIntroString(dietQuery);
            String results = buildElderlyStringGivenDiet(dietQuery);
            if (results.isEmpty()) {
                checkSimilarities(dietMappings.keySet(), dietQuery);
            } else {
                System.out.println(results);
            }
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        }
    }

    /**
     * Returns a consolidated String of all elderly information given real name.
     *
     * @param realName String containing real name to be looked out.
     * @return String containing all the elderly information in the system based on filter.
     */
    public String filterElderlyInformationGivenName(String realName) {
        return elderlyArrayList
                .stream()
                .filter((t) -> t.getName().contentEquals(realName))
                .map(Objects::toString)
                .reduce((t, u) -> t + '\n' + u)
                .orElse("");
    }

    /**
     * Get a list of all the real names in the system.
     *
     * @return Array of all real names.
     */
    public Set<String> getAllRealNames() {
        return elderlyArrayList
                .stream()
                .map(Elderly::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Get a list of all usernames in the system.
     *
     * @return Array of all usernames.
     */
    public Set<String> getAllUserNames() {
        return elderlyArrayList
                .stream()
                .map(Elderly::getUsername)
                .collect(Collectors.toSet());
    }

    /**
     * Iterates through Strings and returns first result that has the highest similarities.
     *
     * @param listOfStrings Set of Strings to search from.
     * @param searchTerm    String with search term.
     */
    public void checkSimilarities(Set<String> listOfStrings, String searchTerm) {
        float highestResult = 0;
        HashMap<Float, String> resultToString = new HashMap<>();
        for (String stringInSet : listOfStrings) {
            float result = re.levenshteinDistance(searchTerm, stringInSet);
            if (highestResult < result) {
                highestResult = result;
            }
            resultToString.put(result, stringInSet);
        }

        // Print out the closest match if similarity more than 0.80
        if (highestResult >= SIMILARITY_INDEX) {
            ui.printClosestMatch(resultToString.get(highestResult));
        } else {
            // Say that you don't understand what the user is looking for
            ui.printCannotFindQuery(searchTerm);
        }
    }

    /**
     * Prints a list of elderly and their details given real name.
     *
     * @param userLine String containing real name to be looked out.
     */
    public void getAllElderlyDetailsByName(String userLine) {
        try {
            // Check if format is correct
            if (!re.isValidFindByName(userLine)) {
                throw new InvalidViewByNameException();
            }
            String realName = parser.getRealNameFromSearchName(userLine);
            ui.printQueryResultsIntroString(realName);
            String results = filterElderlyInformationGivenName(realName);
            if (results.isEmpty()) {
                Set<String> realNameArray = getAllRealNames();
                checkSimilarities(realNameArray, realName);
            } else {
                System.out.println(results);
            }
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        }
    }

    /**
     * Removes elderly from list given username.
     *
     * @param userLine String containing username to be deleted from list.
     */
    public void deleteElderlyByUsername(String userLine) {
        try {
            // Check if format is correct
            if (!re.isValidDeleteElderlyByUsername(userLine)) {
                throw new InvalidDeleteElderlyException();
            }
            String userName = parser.getUserNameFromDeleteElderly(userLine);
            Set<String> allUserNames = getAllUserNames();
            if (allUserNames.contains(userName)) {
                elderlyArrayList
                        .removeIf((t) -> t.getUsername().contentEquals(userName));
                System.gc();
                ui.printDeleteByName(userName);
            } else {
                checkSimilarities(allUserNames, userName);
            }
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        }
    }

    /**
     * Store program's data as JSON into a file using given filepath.
     *
     * @param userLine String containing store command and filepath.
     */
    public void storeFromFilePath(String userLine) {
        try {
            // Checks if the command is issued correctly
            if (!re.isValidStoreFileFromFilePath(userLine)) {
                throw new InvalidStoreToFilePathException();
            }

            String[] paramList = userLine.split(SPLIT_STORE_FILE);
            this.filePath = paramList[INDEX_OF_FILE_PATH];

            // Writes to file path and overwrites all contents if it doesn't exist
            FileWriter fw = new FileWriter(this.filePath);
            JsonWriter jw = gson.newJsonWriter(fw);
            gson.toJson(gson.toJsonTree(elderlyArrayList), jw);
            fw.close();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (IOException e) {
            ui.printExceptionMessage(e);
        }
    }

    /**
     * Returns if file exists based on a given filepath.
     *
     * @param filePath String of the absolute/relative filepath.
     * @return Boolean value.
     */
    private boolean checkIfFileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Load program's data JSON from a file using the given filepath.
     *
     * @param userLine String containing load command and filepath.
     */
    public void loadFromFilePath(String userLine) {
        try {
            // Checks if the command is issued correctly
            if (!re.isValidLoadFileFromFilePath(userLine)) {
                throw new InvalidLoadFromFilePathException();
            }

            String[] paramList = userLine.split(SPLIT_LOAD_FILE);
            String filePath = paramList[INDEX_OF_FILE_PATH];

            // Checks if file exists
            if (checkIfFileExists(filePath)) {
                this.filePath = filePath;
                ui.printFileExists(filePath);
            } else {
                // File does not exist, leave program
                ui.printFileDoesNotExists(filePath);
                return;
            }

            // Reads from file path and loads back into elderly list
            FileReader fr = new FileReader(this.filePath);
            JsonReader jr = gson.newJsonReader(fr);
            elderlyArrayList = gson.fromJson(jr, new TypeToken<ArrayList<Elderly>>() {
            }.getType());
            fr.close();
        } catch (InvalidInputException e) {
            ui.printInvalidInputException(e);
        } catch (IOException e) {
            ui.printExceptionMessage(e);
        } catch (JsonSyntaxException wrongFormatException) {
            ui.printWrongFileSyntax();
        }
    }
}
