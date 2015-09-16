/**
 *  EdfDataSaving.java  
 *  This program is used to record EEG data into an EDF file and load EEG data
 *  from an EDF file. 
 *  
 *  Note: You have to follow these steps to run this program with eclipse:
 *  Entering Run Configurations->environments->new then adding LD_LIBRARY_PATH 
 *  and leading it to folder containing libraries. 
 *  
 *  EDF file is released in the following shape:
 *  File name  : you input
 *  PartientID : "EDFTest"  
 *  RecordID   : "0"
 *  Date       : "07.03.2014"
 *  Time       : "00:00:00" 
 */

/**
 * @author s-300pmu favorite2 + buk-m2e + pantsyr-s1
 *
 */

package com.emotiv.example;

import com.emotiv.example.Edk;
import com.emotiv.example.EdkErrorCode;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.util.Scanner;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EDFExample {

	public static void main(String[] args) {
		IntByReference userID = null;
		IntByReference nSamplesTaken = null;
		int state = 0;
		float secs = 1;
		int option;
		String fileName = null;
		boolean connected = false;
		boolean readytocollect = false;

		System.out.println("\n ========================================"
				+ "===========================");
		System.out.println("\n Example  to show how to record and load "
				+ "the EEG data ");

		try {
			Pointer eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
			Pointer eState = Edk.INSTANCE.EE_EmoStateCreate();

			userID = new IntByReference(0);
			nSamplesTaken = new IntByReference(0);

			Pointer hData = Edk.INSTANCE.EE_DataCreate();
			// Edk.INSTANCE.EE_DataSetBufferSizeInSec(secs);

			// connect to EmoEngine
			if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK
					.ToInt()) {
				System.out.println("Emotiv Engine start up failed.");
				connected = false;
				return;
			} else {
				connected = true;
				Scanner input = new Scanner(System.in);
				Scanner inputFile = new Scanner(System.in);
				Scanner outputFile = new Scanner(System.in);

				while (connected) {
					state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);
					if (state == EdkErrorCode.EDK_OK.ToInt()) {
						int eventType = Edk.INSTANCE
								.EE_EmoEngineEventGetType(eEvent);
						Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);

						if (eventType == Edk.EE_Event_t.EE_UserAdded.ToInt())
							if (userID != null) {
								System.out.println("User added");
								Edk.INSTANCE.EE_DataAcquisitionEnable(
										userID.getValue(), true);
								readytocollect = true;
							}
					} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
						System.out.println("Internal error in Emotiv Engine!");
						break;
					}

					if (readytocollect) {
						Edk.INSTANCE.EE_DataUpdateHandle(0, hData);

						Edk.INSTANCE.EE_DataGetNumberOfSample(hData,
								nSamplesTaken);

						if (nSamplesTaken != null) {
							if (nSamplesTaken.getValue() != 0) {
								System.out
										.println("\n ========================================"
												+ "===========================");
								System.out
										.println("\n Press '1' to record EEG data into EDF file ");
								System.out
										.println("\n Press '2' to load data from EDF file ");
								System.out.println("\n Press '3' to exit ");

								System.out.println("\n Option : ");
								option = input.nextInt();

								switch (option) {

								case 1:
									System.out
											.println("\n Please input the filename : ");
									fileName = inputFile.nextLine();

									Edk.INSTANCE.EE_EdfStartSaving(
											userID.getValue(), fileName,
											"edfTest", "0", "01.01.2013",
											"00:00:00");

									// working something here
									for (int i = 0; i < 10; i++) {
										System.out
												.println(" Saving eeg data...");
										Thread.sleep(1000);
									}

									Edk.INSTANCE.EE_EdfStopSavingAll();
									System.out.println("Success. File "
											+ fileName + " created ! ");
									connected = true;
									break;

								case 2:
									if (connected == true) {
										Edk.INSTANCE.EE_EngineDisconnect();
										readytocollect = false;
									}

									System.out
											.println("\n Please enter full file name: ");
									fileName = outputFile.nextLine();

									if (Edk.INSTANCE
											.EE_EngineLocalConnect(fileName) != EdkErrorCode.EDK_OK
											.ToInt()) {
										System.out
												.println("\n Emotiv Engine with EDF file"
														+ " start up failed. ");
									}

									Edk.INSTANCE.EE_EdfStart();
									System.out.println("\n Load Success! ");
									connected = true;
									break;

								case 3:
									connected = false;
									break;

								default:
									System.out.println("\n wrong choice ");
									connected = true;
									break;
								}

							}
						}
					}

				}

				inputFile.close();
				outputFile.close();
				input.close();

				// disconnect from EmoEngine
				Edk.INSTANCE.EE_EngineDisconnect();
				Edk.INSTANCE.EE_EmoStateFree(eState);
				Edk.INSTANCE.EE_EmoEngineEventFree(eEvent);
				System.out.println("Disconnected from EmoEngine!");
			}
		} catch (UnsatisfiedLinkError exception) {
			System.err.println("\nFailed to load libedk : " + exception);
		} catch (InputMismatchException exception) {
			System.err.println("\nYou input an incorrected input : "
					+ exception);
		} catch (Exception exception) {
			System.err.println("\nError : " + exception);
		}
	}
}
