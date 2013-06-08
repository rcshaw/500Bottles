package com._500bottles.dispatch;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com._500bottles.action.UploadAction;

/**
 * Servlet implementation class NSFWServlet
 */
public class UploadDispatch extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(request);

			for (FileItem item : items)
			{
				if (item.isFormField())
				{
					// Process regular form field (input
					// type="text|radio|checkbox|etc", select, etc).
					String fieldname = item.getFieldName();
					String fieldvalue = item.getString();

					// ... (do your job here)
				} else
				{
					// Process form file field (input type="file").
					String fieldname = item.getFieldName();
					String filename = FilenameUtils.getName(item.getName());

					if (filename != null && filename != "")
					{
						InputStream filecontent = item.getInputStream();

						long id = UploadAction.addPhoto(item, filename
								.substring(filename.lastIndexOf("."),
										filename.length()));
						System.out.println(id);
					}

				}
			}

		} catch (FileUploadException e)
		{
			System.err.println("UploadDispatch: tried to upload image, failed");
		}
	}
}
