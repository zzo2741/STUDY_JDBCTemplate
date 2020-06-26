package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsVO;

public class ReplyActionCommand implements BbsCommandImpl
{
	@Override
	public void execute(Model model)
	{
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest) paramMap.get("req");
		SpringBbsVO vo = (SpringBbsVO) paramMap.get("springBbsVO");
		JDBCTemplateDAO dao = new JDBCTemplateDAO();

		dao.reply(vo);
	}
}
