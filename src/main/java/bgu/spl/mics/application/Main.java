package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		Input input = jsonReader();
		Attack[] attack = input.getAttacks();
		int num = input.getEwoks();
		Thread leia = new Thread(new LeiaMicroservice(attack));
		Thread hanSolo = new Thread(new HanSoloMicroservice());
		Thread c3po = new Thread(new C3POMicroservice());
		Thread r2d2 = new Thread(new R2D2Microservice(input.getR2D2()));
		Thread lando = new Thread(new LandoMicroservice(input.getLando()));
		Ewoks ewoks = Ewoks.getInstance();
		Diary diary = Diary.getInstance();

		for (int i = 0; i < num; i++) {
			ewoks.addEwok(new Ewok(i));
		}

		leia.start();
		hanSolo.start();
		c3po.start();
		r2d2.start();
		lando.start();

		try {
			String output = "output.json";
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(output);
			gson.toJson(diary, writer);
			writer.flush();
			writer.close();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Input jsonReader() {
		try {
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get("input.json"));
			Input input = gson.fromJson(reader, Input.class);
			System.out.println(input);
			reader.close();
			return input;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
