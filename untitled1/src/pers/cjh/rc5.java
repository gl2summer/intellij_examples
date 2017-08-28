package pers.cjh;

public class rc5 {
    static final int RC_W	= (16);//bit
    static final int RC_R	= (12);
    static final int RC_B	= (16);//bytes
    static final int RC_T	= (2*RC_R+2);
    static final int RC_C	= (RC_B*8/RC_W);

    private static short rotl(int x, int y){
        return (short) (((x)<<((y)&(RC_W-1))) | ((x&0xffff)>>>(RC_W-((y)&(RC_W-1)))));
    }
    private static short rotr(int x, int y){
        return (short) (((x&0xffff)>>>((y)&(RC_W-1))) | ((x)<<(RC_W-((y)&(RC_W-1)))));
    }
    public static void encipher(short []in, int in_len, short []out, short []keys){
        short x, y;
        int i,j;
        for(j=0; j<in_len; j+=2){
            x = (short)(in[j] + keys[0]);
            y = (short)(in[j+1] + keys[1]);
            for(i=1; i<=RC_R; i++){
                x = (short) (rotl((x^y),y) + keys[2*i]);
                y = (short) (rotl((y^x),x) + keys[2*i+1]);
                //System.out.printf("%x,%x\n", x,y);
            }
            out[j] = x;
            out[j+1] = y;
        }
    }
    public static void decipher(short []in, int in_len, short []out, short []keys) {
        short x, y;
        int i,j;
        for(j=0; j<in_len; j+=2){
            x = in[j];
            y = in[j+1];
            for(i=RC_R; i>0; i--){
                y = (short) (rotr((y-keys[2*i+1]),x)^x);
                x = (short) (rotr((x-keys[2*i]),y)^y);
            }

            out[j]= (short) (x - keys[0]);
            out[j+1]= (short) (y - keys[1]);
        }
    }
    public static void expand_keys(byte []in, short []out){
        for(int i=0; i < in.length/2; i++){
            out[i] = (short) ((short)in[2*i+1]<<8 | in[2*i]);
        }
    }
}
