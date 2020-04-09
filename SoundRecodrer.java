package notexist.soundtest;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;

public class SoundRecodrer
{


    public static void main(String[] args)
    {

        try
        {
            AudioFormat audioFormat = new AudioFormat(16000f, 16, 1, true, false);
            //DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

            TargetDataLine targetDataLine;//цель, откуда читает звук

            targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
            targetDataLine.open();
            targetDataLine.start();

            File audioFile = new File("recordedAudio.raw");
            FileOutputStream audioFileOutputStrean = new FileOutputStream(audioFile);

            // AudioInputStream audioInputStream=new AudioInputStream(targetDataLine);
            // File audioFile=new File("recordedAudio.wav");
            // AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);

            byte[] data = new byte[1024];
            int blocksRead = 0;
            while (targetDataLine.isOpen() && blocksRead++ < 1000)
            {
                int readBytes = targetDataLine.read(data, 0, data.length);
                //куда писать прочитанное, с какого элемента писать, сколько байт писать
                audioFileOutputStrean.write(data);
                if (readBytes > 0)
                {
                    int i = 0;
                    for (i = 0; i < readBytes; i++)
                    {
                        if (data[i] != 0) break;
                    }
                    if (i >= data.length)
                    {
                        System.out.println("Data is empty!");
                    }
                }
                System.out.println(blocksRead+" blocks read");
            }
            audioFileOutputStrean.close();
            targetDataLine.stop();
            targetDataLine.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
