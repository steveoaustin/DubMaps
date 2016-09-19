package navigator;

import java.awt.EventQueue;

public class DubMain {
	public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                @SuppressWarnings("unused")
				ContentFrame GUI = new ContentFrame();
            }
        });
    }
}