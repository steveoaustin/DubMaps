package mapMaker;

import java.awt.EventQueue;

import view.ContentFrame;

public class MapMakerMain {
	public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
				ContentFrame GUI = new ContentFrame(false);
            }
        });
    }
}
