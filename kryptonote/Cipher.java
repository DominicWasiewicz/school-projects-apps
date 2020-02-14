package ca.yorku.eecs.kryptonote;


public class Cipher {

    private String reader;
    private String key;
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Cipher(String key)
    {
        this.key = key;
    }

    private String makePad(String note) {

        String pad = this.key;
        for (; pad.length() < note.length(); ) {

            pad += this.key;

            for (int i = 0; i < note.length(); i++) {
                String c = note.substring(i, i + 1);
            }

        }
        return pad;

    }

    public String encrypt(String note)
    {

        String pad = makePad(note);
        String result = "";

        for (int i = 0; i < note.length(); i++)
        {
            String c = note.substring(i, i + 1);
            int position = ALPHABET.indexOf(c);
            int shift = Integer.parseInt(pad.substring(i, i + 1));
//            int newPosition = (position + shift) % ALPHABET.length();
            int newPosition = position + shift;
            if (newPosition > ALPHABET.length()) {
                newPosition = newPosition - ALPHABET.length();

            }

            result = result + ALPHABET.substring(newPosition, newPosition + 1);


        }
        return result;
    }

    public String decrypt(String note){

        String pad = makePad(note);
        String result = "";

        for(int i=0; i < note.length(); i++){

            String c = note.substring(i,i+1);
            int position = ALPHABET.indexOf(c);
            int shift = Integer.parseInt(pad.substring(i,i+1));


            int newPosition = (position - shift);
            if(newPosition < 0) {
                newPosition = newPosition + ALPHABET.length();

            }
            result = result + ALPHABET.substring(newPosition, newPosition + 1);

        }

        return result;

    }



}
