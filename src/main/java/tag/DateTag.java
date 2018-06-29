package tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.jsp.JspException;


import javax.servlet.jsp.tagext.TagSupport;

public class DateTag
        extends TagSupport {
    private static final long serialVersionUID = 6464168398214506236L;
    private String value;

    public int doStartTag() throws JspException {
        String vv = "" + this.value;
        try {
            long time = Long.valueOf(vv.trim()).longValue() * 1000L;
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String s = dateformat.format(c.getTime());
            this.pageContext.getOut().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
}
    public void setValue(String value) {
        this.value = value;
    }
}


