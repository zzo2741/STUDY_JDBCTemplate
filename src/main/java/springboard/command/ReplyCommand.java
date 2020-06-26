package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsVO;

@Service("BbsCommandImpl")
public class ReplyCommand implements BbsCommandImpl
{
	@Override
	public void execute(Model model)
	{
		Map<String, Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("req");
		String idx = req.getParameter("idx");
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		SpringBbsVO vo = dao.view(idx);
		/*
		 * 기존 게시물을 가져와서 제목과 내용 부분에 아래와 같이 문자열 처리를 한다.
		 * 내용의 경우 textarea에 표현해야 하므로 <br>태그 대신 \n\r이 들어가야 한다.
		 */
		vo.setTitle("[RE]" + vo.getTitle());
		vo.setContents("\n\r\n\r---[원본글]---\n\r" + vo.getContents());
		model.addAttribute("replyRow", vo);

	}
}
