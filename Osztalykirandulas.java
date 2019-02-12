package szimulatorApp;

import gimnazium.osztaly.Gimnazista;
import szallas.Szoba;
import gimnazium.osztaly.Osztalyfonok;
import java.util.ArrayList;
import java.util.List;


public class Osztalykirandulas {


    public static void main(String args[]) {
        if (args.length != 3) {
            System.err.println("adj meg 3 paramétert");
        } else {
            kirandulas(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
    }


    private static void kirandulas(int ofok, int tanulok, int szobak) {


        if (szobak < 2) {
            System.err.println("kevés a szobák száma legalább 2 szükséges");
        } else {


            /*létrehozza a gimiseket és eldönti a nemüket*/
            Gimnazista gimis[] = new Gimnazista[tanulok];
            gimnazistagyártóloop:
            for (int i = 0; i < tanulok; i++) {
                gimis[i] = new Gimnazista();
                gimis[i].hím = gimis[i].fiu(gimis[i].n);
                System.out.println(gimis[i].hím);
            }

            /*fiuk lanyok szétválogatása*/
            Gimnazista[] fiuk = new Gimnazista[tanulok];
            Gimnazista[] lanyok = new Gimnazista[tanulok];
            int f = 0;
            int L = 0;
            for (int i = 0; i < tanulok; i++) {
                if (gimis[i].hím == true) {
                    fiuk[f] = gimis[i];
                    f++;
                } else {
                    lanyok[L] = gimis[i];
                    L++;
                }
            }
            List<Gimnazista> list = new ArrayList<Gimnazista>();
            for (Gimnazista s : fiuk) {
                if (s != null) {
                    list.add(s);
                }
            }
            fiuk = list.toArray(new Gimnazista[list.size()]);

            List<Gimnazista> list2 = new ArrayList<Gimnazista>();
            for (Gimnazista s : lanyok) {
                if (s != null) {
                    list2.add(s);
                }
            }
            lanyok = list2.toArray(new Gimnazista[list2.size()]);



            /*szobákba bepakolás*/
            Szoba probák[] = new Szoba[szobak];
            int l=0;
            int szobaszam=0;

            /*fiuk kipakolása*/
            fiuszobaloop:
            for (int k = 0; k < szobak; k++){
                    szobaszam++;
                    Szoba proba = new Szoba(8,true);
                    for (int i = 0; i < 8; i++) {
                        proba.lakok[i]=fiuk[l];
                        fiuk[l]=null;
                        l++;
                        if(l==fiuk.length){
                            break;
                        }
                    }
                List<Gimnazista> list3 = new ArrayList<Gimnazista>();
                for (Gimnazista s : proba.lakok) {
                    if (s != null) {
                        list3.add(s);
                    }
                }
                proba.lakok = list3.toArray(new Gimnazista[list3.size()]);

                probák[k] = proba;
                if(l==fiuk.length){
                    break fiuszobaloop;
                }
            }

            /*lányok kipakolása*/
            l=0;
            lanyszobaloop:
            for (int k = szobaszam; k < szobak; k++){
                szobaszam++;
                Szoba proba = new Szoba(8,false);
                proba.foglal(8, false);
                for (int i = 0; i < 8; i++) {
                    proba.lakok[i]=lanyok[l];
                    lanyok[l]=null;
                    l++;
                    if(l==lanyok.length){
                        break;
                    }
                }
                List<Gimnazista> list4 = new ArrayList<Gimnazista>();
                for (Gimnazista s : proba.lakok) {
                    if (s != null) {
                        list4.add(s);
                    }
                }
                proba.lakok = list4.toArray(new Gimnazista[list4.size()]);
                probák[k] = proba;
                if(l==lanyok.length){
                    break lanyszobaloop;
                }
            }


            /*ofok legyártása*/
            Osztalyfonok[] tanarok=new Osztalyfonok[ofok];
            for (int i=0; i<ofok; i++) {
                Osztalyfonok tanar=new Osztalyfonok();
                tanarok[i]=tanar;
            }


            /*foglalkoztatás*/
            int g=0;
            for(;;) {
                g++;
                for(int k=0; k<szobaszam; k++){
                    System.out.println(k + " szoba");
                for(int i=0; i<ofok; i++)
                {
                    List<Gimnazista> listx = new ArrayList<Gimnazista>();
                    for (Gimnazista s : probák[k].lakok) {
                        if (s != null) {
                            listx.add(s);
                        }
                    }
                    probák[k].lakok = listx.toArray(new Gimnazista[listx.size()]);


                    tanarok[i].ellenoriz(probák[k]);


                        if(tanarok[i].figyelmeztetes>=1000) {
                            System.out.println( i +". ofő" + tanarok[i].osszeskiosztottFigyelmeztetesekSzama());
                            System.out.println("az osztály kirándulás " + g + " napig tartott");
                            for(int w=0; w<ofok;w++){
                            System.out.println( w + " osztályfőnők " + tanarok[w].osszeskiosztottFigyelmeztetesekSzama());}
                            System.exit(0);
                        }

                        List<Gimnazista> list5 = new ArrayList<Gimnazista>();
                        for (Gimnazista s : probák[k].lakok) {
                            if (s != null) {
                                list5.add(s);
                            }
                        }
                        probák[k].lakok = list5.toArray(new Gimnazista[list5.size()]);

                        if(probák[k].szabadferohely()<probák[k].osszferohely()){
                            if(probák[k].fiuazoba==true) {
                                int p = 0;
                                for (int x = probák[k].lakok.length + 1; x < 8; x++) {
                                    list5.add(fiuk[p]);
                                    p++;
                                    if (p == fiuk.length) {
                                        break;
                                    }
                                }
                            }
                        }
                            if(probák[k].fiuazoba==false){
                                int q=0;
                                for(int x=probák[k].lakok.length+1;x<8;x++){
                                   list5.add(lanyok[q]);
                                    q++;
                                    if(q==lanyok.length){
                                        break;
                                    }
                                }
                            }

                        if(probák[k].lakok.length==0){
                        boolean ürestábor = true;
                        int first = probák[k].lakok.length;
                        for(int b = 0; b < szobaszam && ürestábor; b++)
                        {
                            if (probák[b].lakok.length != first) ürestábor = false;
                        }
                        if (ürestábor){
                            System.out.println("A tábor üres");
                            System.out.println("az osztály kirándulás " + g + " napig tartott");
                            for(int w=0; w<ofok;w++){
                                System.out.println( w + " osztályfőnők " + tanarok[w].osszeskiosztottFigyelmeztetesekSzama());}
                            System.exit(0);
                        }
                        }
                    }
                }
            }
        }
    }
}