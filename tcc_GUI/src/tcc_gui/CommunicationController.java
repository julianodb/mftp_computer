package tcc_gui;

import java.util.EnumMap;
import java.util.Map;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class CommunicationController {

    private boolean DEBUG = false;
    private boolean SIMPLE_TEST = false;
    private boolean SABOTAGE_CRC = false;
    private boolean SABOTAGE_REGISTER_ADDRESS = false;

    /**
     * @return the DEBUG
     */
    public boolean isDEBUG() {
        return DEBUG;
    }

    /**
     * @param DEBUG the DEBUG to set
     */
    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }

    /**
     * @return the SIMPLE_TEST
     */
    public boolean isSIMPLE_TEST() {
        return SIMPLE_TEST;
    }

    /**
     * @param SIMPLE_TEST the SIMPLE_TEST to set
     */
    public void setSIMPLE_TEST(boolean SIMPLE_TEST) {
        this.SIMPLE_TEST = SIMPLE_TEST;
    }

    /**
     * @return the SABOTAGE_CRC
     */
    public boolean isSABOTAGE_CRC() {
        return SABOTAGE_CRC;
    }

    /**
     * @param SABOTAGE_CRC the SABOTAGE_CRC to set
     */
    public void setSABOTAGE_CRC(boolean SABOTAGE_CRC) {
        this.SABOTAGE_CRC = SABOTAGE_CRC;
    }

    /**
     * @return the SABOTAGE_REGISTER_ADDRESS
     */
    public boolean isSABOTAGE_REGISTER_ADDRESS() {
        return SABOTAGE_REGISTER_ADDRESS;
    }

    /**
     * @param SABOTAGE_REGISTER_ADDRESS the SABOTAGE_REGISTER_ADDRESS to set
     */
    public void setSABOTAGE_REGISTER_ADDRESS(boolean SABOTAGE_REGISTER_ADDRESS) {
        this.SABOTAGE_REGISTER_ADDRESS = SABOTAGE_REGISTER_ADDRESS;
    }

    public enum Register {

        EMERGENCY_STOP,SENSOR1,SENSOR2,SENSOR3,SENSOR4,SENSOR5,SENSOR6,SENSOR7,SENSOR8,SENSOR9,
        SENSOR10,SENSOR11,SENSOR12,SENSOR13,SENSOR14,SENSOR15,SENSOR16,SENSOR17,SENSOR18,SENSOR19,SENSOR20,
        TYPE_RECORD,TYPE_ID,TYPE_PARA,TYPE_PARB,TYPE_MULT,SENSOR_RECORD,SENSOR_ID,SENSOR_TYPE,SENSOR_ADDRESS,
        MOTOR_RECORD,MOTOR_ID,MOTOR_PWM1,MOTOR_PWM2,MOTOR_CNA,MOTOR_CNB,MOTOR_PPV,MOTOR_COURSE,MOTOR_REDUCTION,
        MOTOR_VMAX,MOTOR_KP,MOTOR_TP,MOTOR_POSINI,SPEED_RECORD,SPEED_MOTOR_ID,SPEED_VALUE,TARGET_RECORD,
        TARGET_MOTOR_ID,TARGET_VALUE,MOVE_RECORD,MOVE_MOTOR_ID,MOVE_VALUE,READPOS_MOTOR_ID,READPOS_READY,READPOS_VALUE
        
    };
    public static int SUCCESS = 0;
    public static int CRC_ERROR = -3;
    public static int TIMEOUT_ERROR = -2;
    public static int NUMBER_OF_RETRIES = 20;
    public static long FRAME_TIMEOUT = 150;
    private Map<Register, Integer> addressMap = new EnumMap<Register, Integer>(Register.class);
    private String commPortName = null;
    private SerialPort serialPort = null;
    private InputStream inputSerialStream = null;
    private OutputStream outputSerialStream = null;
    private String sync = new String();
    private int baudRate = 9600;
    private int parity = SerialPort.PARITY_EVEN;
    private int databits = SerialPort.DATABITS_8;
    private int stopbits = SerialPort.STOPBITS_1;

    public CommunicationController() {
        addressMap.put(Register.EMERGENCY_STOP, 0);
        addressMap.put(Register.SENSOR1, 1);
        addressMap.put(Register.SENSOR2, 2);
        addressMap.put(Register.SENSOR3, 3);
        addressMap.put(Register.SENSOR4, 4);
        addressMap.put(Register.SENSOR5, 5);
        addressMap.put(Register.SENSOR6, 6);
        addressMap.put(Register.SENSOR7, 7);
        addressMap.put(Register.SENSOR8, 8);
        addressMap.put(Register.SENSOR9, 9);
        addressMap.put(Register.SENSOR10, 10);
        addressMap.put(Register.SENSOR11, 11);
        addressMap.put(Register.SENSOR12, 12);
        addressMap.put(Register.SENSOR13, 13);
        addressMap.put(Register.SENSOR14, 14);
        addressMap.put(Register.SENSOR15, 15);
        addressMap.put(Register.SENSOR16, 16);
        addressMap.put(Register.SENSOR17, 17);
        addressMap.put(Register.SENSOR18, 18);
        addressMap.put(Register.SENSOR19, 19);
        addressMap.put(Register.SENSOR20, 20);
        addressMap.put(Register.TYPE_RECORD, 21);
        addressMap.put(Register.TYPE_ID, 22);
        addressMap.put(Register.TYPE_PARA, 23);
        addressMap.put(Register.TYPE_PARB, 24);
        addressMap.put(Register.TYPE_MULT, 25);
        addressMap.put(Register.SENSOR_RECORD, 27);
        addressMap.put(Register.SENSOR_ID, 28);
        addressMap.put(Register.SENSOR_TYPE, 29);
        addressMap.put(Register.SENSOR_ADDRESS, 30);
        addressMap.put(Register.MOTOR_RECORD, 32);
        addressMap.put(Register.MOTOR_ID, 33);
        addressMap.put(Register.MOTOR_PWM1, 34);
        addressMap.put(Register.MOTOR_PWM2, 35);
        addressMap.put(Register.MOTOR_CNA, 36);
        addressMap.put(Register.MOTOR_CNB, 37);
        addressMap.put(Register.MOTOR_PPV, 38);
        addressMap.put(Register.MOTOR_COURSE, 39);
        addressMap.put(Register.MOTOR_REDUCTION, 40);
        addressMap.put(Register.MOTOR_VMAX, 41);
        addressMap.put(Register.MOTOR_KP, 42);
        addressMap.put(Register.MOTOR_TP, 43);
        addressMap.put(Register.MOTOR_POSINI, 44);
        addressMap.put(Register.SPEED_RECORD, 47);
        addressMap.put(Register.SPEED_MOTOR_ID, 48);
        addressMap.put(Register.SPEED_VALUE, 49);
        addressMap.put(Register.TARGET_RECORD, 51);
        addressMap.put(Register.TARGET_MOTOR_ID, 52);
        addressMap.put(Register.TARGET_VALUE, 53);
        addressMap.put(Register.MOVE_RECORD, 54);
        addressMap.put(Register.MOVE_MOTOR_ID, 55);
        addressMap.put(Register.MOVE_VALUE, 56);
        addressMap.put(Register.READPOS_MOTOR_ID, 58);
        addressMap.put(Register.READPOS_READY, 59);
        addressMap.put(Register.READPOS_VALUE, 60);
    } // constructor
    
    public void configure(int baud,int parity, int databits, int stopbits) {
        this.baudRate = baud;
        this.parity = parity;
        this.databits = databits;
        this.stopbits = stopbits;
    } // configure

    public void init(String commPort) throws Exception {
        this.commPortName = commPort;
        connect(this.commPortName);
        
         this.readRegister(1, Register.EMERGENCY_STOP, 1); // send something to heat up the line.
                                                 // also, the arduino board will probably reset so its good to wait for it to initialize ?
        
    } // init

    public void end() throws Exception {
        this.disconnect();
    }

    public CommReturn writeRegister(int destination, Register register, int value) throws Exception {
        CommReturn ret = null;
        synchronized (sync) {
            byte[] frame = buildWriteRegisterFrame(destination, register, value);
            ret = sendRequestGetResponse(frame);
        }
        return ret;
    } // writeRegister

    public CommReturn readRegister(int destination, Register register, int numberOfRegisters) throws Exception {
        CommReturn ret = null;
        synchronized (sync) {
            byte[] frame = buildReadRegisterFrame(destination, register, numberOfRegisters);
            ret = sendRequestGetResponse(frame);
        }
        return ret;
    } // readRegister

    private CommReturn sendRequestGetResponse(byte[] frame) throws Exception {
        CommReturn ret = new CommReturn();
        ret.commSuccess = false;
        ret.exceptionCode = TIMEOUT_ERROR; // timeout
        for (int retries = 0; retries < NUMBER_OF_RETRIES; retries++) {
            sendRequest(frame);
            ret = getResponse();
            if (ret.commSuccess) {
                if (isDEBUG()) {
                    System.out.println("RET.SUCCESS = TRUE");
                }
                if (checkCRC(ret)) {
                    if (isDEBUG()) {
                        System.out.println("CRC OK !");
                    }
                    if (ret.function > 0x80) {
                        ret.executionSuccess = false; // error //[ju:20121106] changed commSuccess to executionSuccess
                    }
                    break;
                } else {
                    ret.commSuccess = false;
                    ret.exceptionCode = CRC_ERROR;
                }
            } else {
                if (isDEBUG()) {
                    System.out.println("Timeout");
                }
            }
        } // for retries
        return ret;
    } // sendRequestGetResponse

    private void sendRequest(byte[] frame) throws Exception {
        if (isDEBUG() || isSIMPLE_TEST()) {
            System.out.print("trying to send: ");
            for (int i = 0; i < frame.length; i++) {
                System.out.print(Integer.toHexString(Integer.parseInt(Byte.toString(frame[i])) & 0x00FF) + " ");
            }
            System.out.println();
        }
        outputSerialStream.write(frame);
        outputSerialStream.flush();
        if (isDEBUG()) {
            System.out.println("sent ok !");
        }
    } // sendRequest

    private CommReturn getResponse() throws Exception {
        CommReturn ret = new CommReturn();
        byte[] buffer = new byte[1024];
        int idx = 0;
        Timer timer = new Timer(FRAME_TIMEOUT);
        timer.start();

        boolean found = false;
        if (isDEBUG()|| isSIMPLE_TEST()) {
            System.out.println("waiting response !");
        }
        while ((!found) && (!timer.timedOut())) {
            if (inputSerialStream.available() > 0) {
                int chInt = inputSerialStream.read();
                if (isDEBUG() || isSIMPLE_TEST()) {
                    System.out.print(" " + Integer.toHexString(chInt));
                }
                if (chInt != -1) {
                    buffer[idx] = (byte) chInt;
                    switch (idx) {
                        case 1:
                            ret.function = buffer[1] & 0x00FF;
                            if (isDEBUG()) {
                                System.out.println("function : " + Integer.toHexString(ret.function));
                            }
                            break;

                        case 2:
                            if (ret.function > 0x80) {
                                ret.exceptionCode = buffer[2];
                            }
                            break;

                        case 4:
                            if (ret.function > 0x80) { // error
                                if (isDEBUG()) {
                                    System.out.println("error function received ");
                                }
                                ret.size = 3;
                                ret.data = buffer;
                                ret.value = null;
                                ret.commSuccess = true;
                                ret.executionSuccess = false;
                                found = true;
                            }
                            break;

                        case 7:
                            if (ret.function == 6) {
                                ret.size = 6;
                                ret.data = buffer;
                                ret.value = new int[1];
                                ret.value[0] = ((buffer[4] & 0x00FF) << 8) | (buffer[5] & 0x00FF);
                                ret.commSuccess = true;
                                ret.executionSuccess = true;
                                found = true;
                            }
                            break;

                        default:
                            if (ret.function == 3) {
                                if (idx == (buffer[2] & 0x00FF) + 4) {
                                    ret.size = idx - 1;
                                    ret.data = buffer;
                                    int totalFields = (buffer[2] & 0x00FF) / 2;
                                    ret.value = new int[totalFields];
                                    for (int numberOfField = 0; numberOfField < totalFields; numberOfField++) {
                                        ret.value[numberOfField] = ((buffer[(numberOfField * 2) + 3] & 0x00FF) << 8) | (buffer[(numberOfField * 2) + 4] & 0x00FF);
                                    }
                                    ret.commSuccess = true;
                                    ret.executionSuccess = true;
                                    found = true;
                                }
                            }
                            break;
                    }
                    idx++;


                } else {
                    throw new Exception("Port closed");
                }
            } else {
                Thread.sleep(10);
                if (isDEBUG()) {
                    System.out.print(" -");
                }
            }
        } // while !found and !timeout


        if (timer.timedOut()) {
            ret.data = null;
            ret.value = null;
            ret.size = 0;
            ret.commSuccess = false;
            ret.executionSuccess = false;
            ret.function = 0;
            ret.exceptionCode = TIMEOUT_ERROR; // timeout
        }
        if(isDEBUG() || isSIMPLE_TEST()) {
            System.out.println();
        }
        
        return ret;
    } // getResponse

    private byte[] buildReadRegisterFrame(int destination, Register register, int numberOfRegisters) {
        byte[] frame = new byte[8];
        int function03 = 3;
        int startingAddress = addressMap.get(register);

        frame[0] = (byte) (destination & 0x00FF);
        frame[1] = (byte) (function03 & 0x00FF);
        frame[2] = (byte) ((startingAddress >>> 8) & 0x00FF);
        frame[3] = (byte) (startingAddress & 0x00FF);
        frame[4] = (byte) ((numberOfRegisters >>> 8) & 0x00FF);
        frame[5] = (byte) (numberOfRegisters & 0x00FF);
        int crc = calculateCRC(frame, 0, 5);
        frame[6] = (byte) ((crc >>> 8) & 0x00FF);
        frame[7] = (byte) (crc & 0x00FF);
        
        if(SABOTAGE_REGISTER_ADDRESS) {
            byte[] sabotaged_frame = new byte[frame.length-1];
            sabotaged_frame[0] = frame[0];
            sabotaged_frame[1] = frame[1];
            sabotaged_frame[2] = frame[2];
            // one byte of address forgotten ! oops =)
            sabotaged_frame[3] = frame[4];
            sabotaged_frame[4] = frame[5];
            crc = calculateCRC(sabotaged_frame, 0, 4);
            sabotaged_frame[5] = (byte) ((crc >>> 8) & 0x00FF);
            sabotaged_frame[6] = (byte) (crc & 0x00FF);
            return sabotaged_frame;
        }
        
        return frame;
    } // buildReadRegisterFrame

    private byte[] buildWriteRegisterFrame(int destination, Register register, int value) {
        byte[] frame = new byte[8];
        int function06 = 6;
        int startingAddress = addressMap.get(register);

        frame[0] = (byte) (destination & 0x00FF);
        frame[1] = (byte) (function06 & 0x00FF);
        frame[2] = (byte) ((startingAddress >>> 8) & 0x00FF);
        frame[3] = (byte) (startingAddress & 0x00FF);
        frame[4] = (byte) ((value >>> 8) & 0x00FF);
        frame[5] = (byte) (value & 0x00FF);
        int crc = calculateCRC(frame, 0, 5);
        frame[6] = (byte) ((crc >>> 8) & 0x00FF);
        frame[7] = (byte) (crc & 0x00FF);
        
        if(SABOTAGE_REGISTER_ADDRESS) {
            byte[] sabotaged_frame = new byte[frame.length-1];
            sabotaged_frame[0] = frame[0];
            sabotaged_frame[1] = frame[1];
            sabotaged_frame[2] = frame[2];
            // one byte of address forgotten ! oops =)
            sabotaged_frame[3] = frame[4];
            sabotaged_frame[4] = frame[5];
            crc = calculateCRC(sabotaged_frame, 0, 4);
            sabotaged_frame[5] = (byte) ((crc >>> 8) & 0x00FF);
            sabotaged_frame[6] = (byte) (crc & 0x00FF);
            return sabotaged_frame;
        }
        return frame;
    } // buildWriteRegisterFrame

    private void connect(String portName) {
        try{
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            if (isDEBUG()) {
                System.out.println("Error: Port is currently in use");
            }
            //@TODO: retry !
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(baudRate, databits, stopbits, parity);
                inputSerialStream = serialPort.getInputStream();
                outputSerialStream = serialPort.getOutputStream();
            } else {
                if (isDEBUG()) {
                    System.out.println("Error: Only serial ports are handled.");
                    //@TODO: retry other names !
                }
            }
        }   
        } catch (Exception e) {
            if (isDEBUG()) {
                System.out.println("Error: Couldn´t find a port named "+portName);
            }
            
        }
    } // connect

    private void disconnect() {
        serialPort.close();
    } // disconnect

    private int calculateCRC(byte[] buf, int start, int end) {
        int i, j;
        int temp, temp2, flag;

        temp = 0xFFFF;
        
        for (i = start; i <= end; i++) {
            temp = (temp ^ (buf[i] & 0x00FF)) & 0xFFFF;

            for (j = 1; j <= 8; j++) {
                flag = temp & 0x0001;
                temp = temp >>> 1 & 0xFFFF;
                if (flag != 0) {
                    temp = (temp ^ 0xA001) & 0xFFFF;
                }
            }
        }

        /* Reverse byte order. */
        if (isDEBUG()) {
            System.out.println(Integer.toHexString(temp));
        }

        temp2 = ((temp >>> 8) & 0x00FF);
        temp = ((temp << 8) | temp2);
        temp &= 0xFFFF;
        if (isDEBUG() || isSABOTAGE_CRC()) {
            System.out.println("CRC: " + Integer.toHexString(temp));
        }
        if (isSABOTAGE_CRC()) {
            temp--;
            temp &= 0xFFFF;
            System.out.println("SABOTAGED CRC: " + Integer.toHexString(temp));
        }

        return (temp);

    } // calculateCRC

    private boolean checkCRC(CommReturn ret) {
        int calculatedCRC = calculateCRC(ret.data, 0, ret.size - 1);
        int rxhi = (ret.data[ret.size] & 0x00FF) << 8;
        int rxlo = ret.data[ret.size + 1] & 0x00FF;
        int rxCRC = (rxhi | rxlo);
        if (isDEBUG()) {
            System.out.println("calculate=" + Integer.toHexString(calculatedCRC) + " rx=" + Integer.toHexString(rxCRC));
        }
        if (calculatedCRC == rxCRC) {
            return true;
        }
        return false;
    } // checkCRC
    
} // CommunicationController
