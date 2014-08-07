package core.trial;

public class SampleResponse
{
	public final double	responseTime;
	public final int	position, correctionTrial, viewTimeAbort;

	public final int	xpos, ypos;

	public SampleResponse(int lp, double rt, int ct)
	{
		this(lp, rt, ct, -1, -1);
	}

	public SampleResponse(int lp, double rt, int ct, int xpos, int ypos)
	{
		this(lp, rt, ct, 0, -1, -1);
	}

	public SampleResponse(int lp, double rt, int ct, int vta, int xpos, int ypos)
	{
		position = lp;
		responseTime = rt;
		correctionTrial = ct;

		this.viewTimeAbort = vta;

		this.xpos = xpos;
		this.ypos = ypos;
	}

}
