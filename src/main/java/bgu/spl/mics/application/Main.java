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
		try {
			Input json = JsonInputReader.getInputFromJson("/home/spl211/IdeaProjects/SPLAssignment2/input.json");
			Diary diary = Diary.getInstance();
			Ewoks ewoks = Ewoks.getInstance();
			int numOfEwoks = json.getEwoks();
			Attack[] attack = json.getAttacks();
			for (int i = 0; i < numOfEwoks; i++) {
				ewoks.addEwok(new Ewok(i+1));
			}

			Thread leia = new Thread(new LeiaMicroservice(attack));
			Thread hanSolo = new Thread(new HanSoloMicroservice());
			Thread c3po = new Thread(new C3POMicroservice());
			Thread r2d2 = new Thread(new R2D2Microservice(json.getR2D2()));
			Thread lando = new Thread(new LandoMicroservice(json.getLando()));



			leia.start();
			hanSolo.start();
			c3po.start();
			r2d2.start();
			lando.start();


			try {
				leia.join();
				hanSolo.join();
				c3po.join();
				r2d2.join();
				lando.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				FileWriter writer = new FileWriter("/home/spl211/IdeaProjects/SPLAssignment2/output.json");
				gson.toJson(diary, writer);
				writer.flush();
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}







	}
/*
	private static Input jsonReader() {
		try {
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get("input.json"));
			Input input = gson.fromJson(reader, Input.class);
			reader.close();
			return input;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

 */
}
