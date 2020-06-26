package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsVO;

public class EditCommand implements BbsCommandImpl
{
	@Override
	public void execute(Model model)
	{
		// 파라미터 한번에 받기....
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest) paramMap.get("req");
		String idx = req.getParameter("idx");
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		SpringBbsVO vo = dao.view(idx);
		model.addAttribute("viewRow", vo);
		dao.close();
	}
}
