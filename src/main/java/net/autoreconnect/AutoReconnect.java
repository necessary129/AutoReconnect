package net.autoreconnect;

import net.fabricmc.api.ModInitializer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoReconnect implements ModInitializer
{
	public static int attempt = 0;
	public static boolean connect = false;

	private static final AtomicInteger countdown = new AtomicInteger();
	private static Timer timer = null;

	@Override
	public void onInitialize() { }

	public static void startCountdown(int seconds)
	{
		countdown.set(seconds);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				if (countdown.decrementAndGet() <= 0)
				{
					connect = true;
					cancel();
				}
			}
		}, 1000, 1000);
	}

	private static void cancel()
	{
		if (timer == null) return;
		timer.cancel();
		timer = null;
	}

	public static void reset()
	{
		cancel();
		attempt = 0;
		connect = false;
		System.out.println("reset");
	}

	public static int getCountdown()
	{
		return countdown.get();
	}
}
