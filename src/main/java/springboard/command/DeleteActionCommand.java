package springboard.command;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import springboard.model.JDBCTemplateDAO;

@Service
public class DeleteActionCommand implements BbsCommandImpl
{
	JDBCTemplateDAO dao;

	@Autowired
	public void setDao(JDBCTemplateDAO dao)
	{
		this.dao = dao;
		System.out.println("JDBCTemplateDAO자동주입(DeleteAction)");
	}

	@Override
	public void execute(Model model)
	{
		Map<String, Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("req");
		// 폼값 받기
		String idx = req.getParameter("idx");
		String pass = req.getParameter("pass");
		//JDBCTemplateDAO dao = new JDBCTemplateDAO();
		dao.delete(idx, pass);
	}
}
