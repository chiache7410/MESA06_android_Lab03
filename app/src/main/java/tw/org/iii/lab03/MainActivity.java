package tw.org.iii.lab03;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private String strAnswer;
    private TextView mesg;
    private int intCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText)findViewById(R.id.input);
        mesg = (TextView)findViewById(R.id.mesg);
        strAnswer = createAnswer(3);
    }
    public void guess(View v){
        intCounter++;
        String guessText = input.getText().toString();
        String result = checkAB(strAnswer, guessText);
        mesg.append(intCounter + ". " + guessText + "=>" + result + "\n");
        if(result.equals("3A0B")){
            //Winner
            showDialog(true);
        }else if(intCounter == 10){
            //Loser
            showDialog(false);
        }
        input.setText("");
    }
    //顯示對話框
    private void showDialog(boolean isWinner){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isWinner?"Winner":"Loser");
        builder.setMessage(isWinner?"恭喜老爺":"謎底為:" + strAnswer);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                restart(null);
            }
        });
        dialog = builder.create();
        dialog.show();
    }
    public void restart(View v){
        mesg.setText("");
        intCounter = 0;
        strAnswer = createAnswer(3);
    }
    public void exit(View v){
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    //JAVA猜數字
    static String createAnswer(int n){
        int[] poker = new int[n]; // poker[0] = 0, ....
        int temp; boolean isRepeat;
        for (int i=0; i<poker.length; i++){
            do{
                temp = (int)(Math.random()*10);
                // 檢查機制
                isRepeat = false;
                for (int j=0; j<i; j++){
                    if (poker[j] == temp){
                        isRepeat = true;
                        break;
                    }
                }
            }while (isRepeat);
            poker[i] = temp;
        }
        String ret = "";
        for (int v : poker) ret += v;
        Log.v("brad", ret);
        return ret;
    }
    static String checkAB(String a, String g){
        int A, B; A = B = 0;
        for (int i=0; i<a.length(); i++){
            if (g.charAt(i) == a.charAt(i)){
                A++;
            }else if (a.indexOf(g.charAt(i)) != -1){
                B++;
            }
        }
        return A + "A" + B + "B";
    }
}
