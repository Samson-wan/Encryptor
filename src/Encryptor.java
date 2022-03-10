import java.util.ArrayList;
import java.util.Arrays;

public class Encryptor
{
  /** A two-dimensional array of single-character strings, instantiated in the constructor */
  private String[][] letterBlock;

  /** The number of rows of letterBlock, set by the constructor */
  private int numRows;

  /** The number of columns of letterBlock, set by the constructor */
  private int numCols;

  /** Constructor*/
  public Encryptor(int r, int c)
  {
    letterBlock = new String[r][c];
    numRows = r;
    numCols = c;
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
      
    }
    return str;
  }
}
// 2 3
// 2 3
// 4 3
// 5 6
// 2 4
