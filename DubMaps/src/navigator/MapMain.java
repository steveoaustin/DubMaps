package navigator;

import java.awt.EventQueue;

import view.ContentFrame;

public class MapMain {
	public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                @SuppressWarnings("unused")
				ContentFrame GUI = new ContentFrame(true);
            }
        });
    }
}