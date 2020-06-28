package springboard.command;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsVO;
import springboard.util.EnvFileReader;
import springboard.util.PagingUtil;

/*
 * BbsCommandImpl를 구현하였으므로 execute()메소드는 반드시 오버라이딩 해야 한다.
 * 또한 해당 객체는 부모타입인 BbsCommandImpl로 참조할 수 있다.
 */
public class ListCommand implements BbsCommandImpl
{
	/*
	 * 컨트롤러에서 인자로 전달해 준 model객체를 매개변수로 전달받는다.
	 * model객체에는 사용자가 요청한 정보인 request객체가 저장되어 있다.
	 */
	@Override
	public void execute(Model model)
	{
		System.out.println("ListCommand > execute() 호출");
		/*
		 * 컨트롤러에서 넘겨준 파라미터를 asMap() 메소드를 통해 Map컬렉션으로 변환한다.
		 * 그리고 request객체를 형변환하여 가져온다.
		 */
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest) paramMap.get("req");
		// DAO객체 생성
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		// 검색어 관련 폼값 처리
		String addQueryString = "";
		String searchColumn = req.getParameter("searchColumn");
		String searchWord = req.getParameter("searchWord");
		if (searchWord != null)
		{
			addQueryString = String.format("searchColumn=%s&searchWord=%s&", searchColumn, searchWord);
			paramMap.put("Column", searchColumn);
			paramMap.put("Word", searchWord);
		}
		// 전체 레코드 수 카운트 하기
		int totalRecordCount = dao.getTotalCount(paramMap);
		/////////////////////////////////////////////////////
		// 페이지 처리 부분 start
		// Environment객체를 이용한 외부파일 읽어오기
		int pageSize = Integer.parseInt(EnvFileReader.getValue("SpringBbsInit.properties", "springBoard.pageSize"));
		int blockPage = Integer.parseInt(EnvFileReader.getValue("SpringBbsInit.properties", "springBoard.blockPage"));
		// 전체 페이지 수 계산
		int totalPage = (int) Math.ceil((double) totalRecordCount / pageSize);
		// 현재페이지 번호, 첫 진입이라면 무조건 1페이지로 지정
		int nowPage = req.getParameter("nowPage") == null ? 1 : Integer.parseInt(req.getParameter("nowPage"));
		int start = (nowPage - 1) * pageSize + 1;
		int end = nowPage * pageSize;
		paramMap.put("start", start);
		paramMap.put("end", end);

		// 페이지 처리 부분 end
		/////////////////////////////////////////////////////
		// 출력할 리스트 가져오기
		ArrayList<SpringBbsVO> listRows = dao.list(paramMap);
		// 가상번호 계산하여 부여하기
		int virtualNum = 0;
		int countNum = 0;
		for (SpringBbsVO row : listRows)
		{
			/* 전체 게시물의 갯수에서 하나씩 차감하면서 가상 번호 부여
			virtualNum = totalRecordCount--;
			row.setVirtualNum(virtualNum);*/
			// 페이지 번호 적용하면 가상번호 부여
			virtualNum = totalRecordCount - (((nowPage - 1) * pageSize) + countNum++);
			row.setVirtualNum(virtualNum);
			// 답변글에 대한 리스트 처리(re.gif이미지를 제목에 삽입)
			String reSpace = "";
			// 해당 게시물의 indent가 0보다 크다면(답변글 이라면)...
			if (row.getBindent() > 0)
			{
				// indent의 크기만큼 공백(&nbsp;)를 추가해준다.
				for (int i = 0; i < row.getBindent(); i++)
				{
					reSpace += "&nbsp;&nbsp;";
				}
				// reply이미지를 추가해준다.
				row.setTitle(reSpace + "<img src = '../images/re3.gif'>" + row.getTitle());
			}
		}
		// model객체에 출력리스트 저장
		String pagingImg = PagingUtil.pagingImg(totalRecordCount, pageSize, blockPage, nowPage,
				req.getContextPath() + "/board/list.do?" + addQueryString);
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("listRows", listRows);
		// JDBCTemplate에서는 자원반납을 하지 않는다.
		// dao.close();
	}

}
