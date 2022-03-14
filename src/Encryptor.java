import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Encryptor
{
  /** A two-dimensional array of single-character strings, instantiated in the constructor */
  private String[][] letterBlock;

  /** The number of rows of letterBlock, set by the constructor */
  private int numRows;

  /** The number of columns of letterBlock, set by the constructor */
  private int numCols;

  private int rowShift;
  Scanner scan;
  /** Constructor*/
  public Encryptor(int r, int c, int rowShift)
  {
    letterBlock = new String[r][c];
    numRows = r;
    numCols = c;
    this.rowShift = rowShift;
    scan = new Scanner(System.in);
  }
  
  public String[][] getLetterBlock()
  {
    return letterBlock;
  }
  
  /** Places a string into letterBlock in row-major order.
   *
   *   @param str  the string to be processed
   *
   *   Postcondition:
   *     if str.length() < numRows * numCols, "A" in each unfilled cell
   *     if str.length() > numRows * numCols, trailing characters are ignored
   */
  public void fillBlock(String str) {
    int index = 0;
    for (int i = 0; i < letterBlock.length; i++) {
      for (int n = 0; n < letterBlock[i].length; n++) {
        if (index > str.length() - 1) {
          letterBlock[i][n] = "A";
        }
        else {
          letterBlock[i][n] = str.substring(index, index + 1);
        }
        index++;
      }
    }
  }

  /** Extracts encrypted string from letterBlock in column-major order.
   *
   *   Precondition: letterBlock has been filled
   *
   *   @return the encrypted string from letterBlock
   */
  public String encryptBlock()
  {
    String str = "";
    for(int j = 0; j < letterBlock[0].length; j++){
      for(int i =0; i < letterBlock.length; i++){
        str += letterBlock[i][j];
      }
    }
    return str;
  }

  /** Encrypts a message.
   *
   *  @param message the string to be encrypted
   *
   *  @return the encrypted message; if message is the empty string, returns the empty string
   */
  public String encryptMessage(String message)
  {
    String output = "";
    String remain = "";
    int segment  = numCols * numRows;
    int loopTime = message.length() / segment;
    int remainLength = message.length() % segment;
      remain = message.substring((message.length() - remainLength));
    for(int i = 0; i < loopTime; i++){
      fillBlock(message.substring(i*segment, (i+1)*segment));
      output += encryptBlock();
    }
    fillBlock(remain);
    if (remainLength != 0) {
      output += encryptBlock();
    }
    return output;
  }
  
  /**  Decrypts an encrypted message. All filler 'A's that may have been
   *   added during encryption will be removed, so this assumes that the
   *   original message (BEFORE it was encrypted) did NOT end in a capital A!
   *
   *   NOTE! When you are decrypting an encrypted message,
   *         be sure that you have initialized your Encryptor object
   *         with the same row/column used to encrypted the message! (i.e. 
   *         the �encryption key� that is necessary for successful decryption)
   *         This is outlined in the precondition below.
   *
   *   Precondition: the Encryptor object being used for decryption has been
   *                 initialized with the same number of rows and columns
   *                 as was used for the Encryptor object used for encryption. 
   *  
   *   @param encryptedMessage  the encrypted message to decrypt
   *
   *   @return  the decrypted, original message (which had been encrypted)
   *
   *   TIP: You are encouraged to create other helper methods as you see fit
   *        (e.g. a method to decrypt each section of the decrypted message,
   *         similar to how encryptBlock was used)
   */
  public String decryptMessage(String encryptedMessage)
  {
    String str = "";
    String remain = "";
    String[][] box = new String[numRows][numCols];
    int segment  = numCols * numRows;
    int loopTime = encryptedMessage.length() / segment;
    int remainLength = encryptedMessage.length() % segment;
    remain = encryptedMessage.substring((encryptedMessage.length() - remainLength));
    for(int i = 0; i < loopTime; i++){
      String messageSegment = encryptedMessage.substring(i*segment, (i+1)*segment);
      int index = 0;
      for(int j = 0; j <box[0].length; j++){
        for(int k = 0; k < box.length; k++){
          box[k][j] = messageSegment.substring(index, index + 1);
          index++;
        }
      }
      for(String[] row : box){
        for(String letter : row){
          str += letter;
        }
      }
      int count = 0;
      for(int a = str.length(); a >=0; a--){
        if(str.substring(a-1, a).equals("A")){
          count++;
        }
        else{
          break;
        }
      }
      str = str.substring(0, str.length() - count);
    }
    return str;
  }

  public String superEncryptMessage(String message){ //row shift
    String str = "";
    int shiftUp = 0;
    int shiftDown = 0;
    fillBlock(message);
    String[][]box = new String[numRows][numCols];
    if(rowShift > 0) {
      shiftDown = rowShift % numRows;
      shiftUp = numRows - shiftDown;
    }
    else{
      shiftUp = Math.abs(rowShift) % numRows;
      shiftDown = numRows - shiftUp;
    }
    System.out.println(shiftDown);
    System.out.println(shiftUp);
    for (int i = 0; i < shiftUp; i++) {
      for (int j = 0; j < numCols; j++) {
        box[i + shiftDown][j] = letterBlock[i][j];
      }
    }
    for(int i = shiftUp; i < numRows; i++){
      for(int j = 0; j < numCols; j++){
        box[i - shiftUp][j] = letterBlock[i][j];
      }
    }

    for(int i = 0; i < box.length; i++){
      for(int j = 0; j < box[0].length; j++){
        str += box[i][j];
      }
    }
    return str;
  }

  public String superDecryptMessage(String message){ //Decrypt Row shift
    rowShift = numRows - rowShift;
    String str = superEncryptMessage(message);
    return str;
  }

//  public int askIndex(){
//    int index = 0;
//    System.out.print("Row Encrypt(e) or Row Decrypt(d): ");
//    String answer = scan.nextLine();
//    if(answer.equals("e")){
//      System.out.print("Please input for numbers of row to shift (positive number shifts down, negative number shifts up): ");
//      index = scan.nextInt();
//    }
//    return index;
//  }
}

