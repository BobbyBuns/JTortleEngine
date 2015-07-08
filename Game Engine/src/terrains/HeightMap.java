package terrains;

import gameInstantiation.MainGameLoop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import dataManager.Utilities;


public class HeightMap {
	private float[][] heights;
	public int verticeSideAmount = 0;
	
	public HeightMap(String fileName) {
		InputStream terrainFile = ClassLoader.getSystemClassLoader().getResourceAsStream(Utilities.maps+fileName+".txt");
		InputStream terrainFile2 = ClassLoader.getSystemClassLoader().getResourceAsStream(Utilities.maps+fileName+".txt");		
		BufferedReader file;
		BufferedReader lines;
		try {
			file = new BufferedReader(new InputStreamReader(terrainFile));
			lines = new BufferedReader(new InputStreamReader(terrainFile2));
			verticeSideAmount = lines(lines);
			parseFile(file);		
		} catch(Exception e) {
			e.printStackTrace();
			MainGameLoop.shutDownProgram();
		}
		
	}

	private int lines(BufferedReader lines){
		int linesN = 0;
		try {
			while(lines.readLine() != null){
			    linesN++;
			}
		} catch(IOException e){
			e.printStackTrace();
			MainGameLoop.shutDownProgram();
		}
		return linesN;
	}
	
	public float getHeight(int vertexX, int vertexZ) {
		return heights[vertexX][vertexZ];
	}
	
	private void parseFile(BufferedReader file){
		heights = new float[verticeSideAmount][verticeSideAmount];

		try{
			String line = "";
			int y = 0;
			while((line = file.readLine()) != null){
				String[] stringNumbers = line.split(",");
				float[] numbers = new float[stringNumbers.length];
				for(int n = 0; n < stringNumbers.length; n++){
					numbers[n] = Float.valueOf(stringNumbers[n]) * (Terrain.SIZE / 10);
				}
				heights[y] = numbers;
				y++;
			}
		}catch(Exception e){
			e.printStackTrace();
			MainGameLoop.shutDownProgram();
		}
	}
}
