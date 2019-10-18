package com.assign.mountainserv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MountainServ
 */
@WebServlet("/MountainServ")
public class MountainServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MountainServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try 
		{
			int maxHeight = Integer.parseInt(request.getParameter("maxHeight"));
			int valWidth =  Integer.parseInt(request.getParameter("valWidth"));
			int plaWidth =  Integer.parseInt(request.getParameter("plaWidth"));
			int tilesHeight = maxHeight + (plaWidth > 0 ? 1 : 0);
			StringBuilder sb = new StringBuilder("<html><pre>");
			if (maxHeight > 0)
			{
				for (int i = tilesHeight-1; i > -1; i--)//loop through each row starting from top
				{
					for (int j = 0; j < maxHeight; j++)
					{
						sb.append(DrawMountainRow(i,j+1,plaWidth));
						if(j >= 0 && j < maxHeight-1 && valWidth > 0)//we only add space if there are valleys
						{
							for (int x = 0; x < valWidth; x++)
							{
								if (i > 0) sb.append(' ');//don't add valley as we haven't drawn the ground yet
								else sb.append('_');//add valley
							}
						}
					}
					sb.append("<br>");
				}
			}
			else
			{
				sb.append("Please enter a height higher than 0!");
			}
			sb.append("</pre></html>");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			System.out.println(sb.toString());
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		//doGet(request, response);
	}

	private String DrawMountainRow(int r, int maxH, int p)
	{
		StringBuilder sb = new StringBuilder("");
		int width = maxH * 2 + p;
		for (int i = 0; i < width; i++)
		{
			int endL = maxH - 1;//index where the left side ends
			int startR = endL + p + 1;//index where right side begins
			if (r < maxH && (i == r || i == width - 1 - r))
			{
				if (i < endL+1) sb.append('/');
				else sb.append('\\');
			}
			else if (p > 0 && r == maxH)//we only care if there's a plateau and we're on the row right above
			{
				if (i > endL && i < startR)//plateau
				{
					sb.append('_');
				}
				else
				{
					sb.append(' ');
				}
			}
			else sb.append(' ');
		}
		return sb.toString();
	}
}
