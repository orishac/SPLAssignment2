package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.*;


/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		Attack[] attack = input.getAttacks();
		int num = input.getEwoks();
		Thread leia = new Thread(new LeiaMicroservice(attack));
		Thread hanSolo = new Thread(new HanSoloMicroservice());
		Thread c3po = new Thread(new C3POMicroservice());
		Thread r2d2 = new Thread(new R2D2Microservice(input.getR2D2()));
		Thread lando = new Thread(new LandoMicroservice(input.getLando()));
		Ewoks ewoks = Ewoks.getInstance();
		Diary diary = Diary.getInstance()

		for (int i = 0; i < num; i++) {
			ewoks.addEwok(new Ewok(i));
		}

		leia.start();
		hanSolo.start();
		c3po.start();
		r2d2.start();
		lando.start();

		leia.run();
		hanSolo.run();
		c3po.run();
		r2d2.run();
		lando.run();

		try {
		String output = "";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(output);
		gson.toJson(diary, writer);
		writer.flush();
		writer.close();


	} catch (IOException e) {
			e.printStackTrace();
		}

		private static Input jsonRead() {
	}
}
