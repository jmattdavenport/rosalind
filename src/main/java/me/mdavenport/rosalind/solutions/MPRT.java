package me.mdavenport.rosalind.solutions;

import com.google.common.collect.ImmutableList;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Matt on 12/5/2015.
 */
public class MPRT {

  private static final String uniprotURL = "http://www.uniprot.org/uniprot/%s.fasta";
  private static final Pattern nGlycosylation = Pattern.compile("N[^P][ST][^P]");

  public static void main(String[] args) throws IOException {
    String className = new Object() {
    }.getClass().getEnclosingClass().getSimpleName();
    String inputName = "rosalind_" + className.toLowerCase() + ".txt";
    String outputName = "out.txt";
    File inputFile = new File(inputName);
    File outputFile = new File(outputName);
    try (Scanner in = new Scanner(inputFile); PrintWriter out = new PrintWriter(outputFile)) {
      while (in.hasNextLine()) {
        String output = process(in.nextLine());
        if (output != null) {
          System.out.println(output);
          out.println(output);
        }
      }
    }
  }

  private static String process(String input) throws IOException {
    List<Integer> locations = locationsOfNGlycosylation(input);
    if (!locations.isEmpty()) {
      return input + '\n' + locations.stream().map(String::valueOf).collect(Collectors.joining(" "));
    } else {
      return null;
    }
  }

  private static List<Integer> locationsOfNGlycosylation(String proteinID) throws IOException {
    String proteinString = getProteinString(proteinID);
    List<Integer> locations = new ArrayList<>();
    Matcher matcher = nGlycosylation.matcher(proteinString);
    int lastIndex = 0;
    while (matcher.find(lastIndex)) {
      lastIndex = matcher.start() + 1;
      System.out.println(lastIndex);
      locations.add(lastIndex);
    }
    return locations;
  }

  private static String getProteinString(String proteinID) throws IOException {
    String url = String.format(uniprotURL, proteinID);
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    Response response = client.newCall(request).execute();
    String fasta = response.body().string();
    System.out.println(fasta);
    return fasta.substring(fasta.indexOf('\n') + 1).replaceAll("\n", "");
  }
}
