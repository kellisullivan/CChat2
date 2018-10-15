/** Implements Caesar cypher */
public class Crypto {
    private int rotation;
    
    /** Make a Crypto using a particular key
     * @param the key
     */
    public Crypto(int r) {
        rotation = r;
    }
    
    /** Encrypt data using the key
     * @param plain the plaintext to encrypt
     * @returns the cyphertext
     */
    public String encrypt(String plain) {
        char[] c=plain.toCharArray();
        for(int i=0; i<c.length; i++) {
            if('a'<=c[i] && 'z'>=c[i]) {
                c[i] = (char)(((c[i]-'a')+rotation)%26 + 'a');
            } else if('A'<=c[i] && 'Z'>=c[i]) {
                c[i] = (char)(((c[i]-'A')+rotation)%26 + 'A');
            } //else if((48 <= (int) c[i] && 57 >= (int) c[i]) || c[i] == '.') {
            	//c[i] = (char) ((((int) c[i]- (int) 'a')+rotation)%10 + (int) 'a');
            //}
        }
        return new String(c);
    }
    
    /** Decrypt data using the key
     * @param cypher the cyphertext to decrypt
     * @returns the plaintext
     */
    public String decrypt(String cypher) {
        char[] c=cypher.toCharArray();
        for(int i=0; i<c.length; i++) {
            if('a'<=c[i] && 'z'>=c[i]) {
                c[i] = (char)(((c[i]-'a')-rotation+26)%26 + 'a');
            } else if('A'<=c[i] && 'Z'>=c[i]) {
                c[i] = (char)(((c[i]-'A')-rotation+26)%26 + 'A');
            } //else if((48 <= (int) c[i] && 57 >= (int) c[i]) || c[i] == '.') {
            	//c[i] = (char) ((((int) c[i]- (int) 'a')-rotation+10)%10 + (int) 'a');
            //}
        }
        return new String(c);
    }
    
    public static void main(String[] args) {
        System.out.println("Making crypto with key 13");
        Crypto c = new Crypto(13);
        String plain = "653546.";
        String cypher;
        System.out.println("Encrypting: "+plain);
        cypher = c.encrypt(plain);
        System.out.println("Got: "+cypher);
        System.out.println("Decrypting: "+cypher);
        plain = c.decrypt(cypher);
        System.out.println("Got: "+plain);
    }
}