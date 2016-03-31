/**
 * 
 * @author J.Stec
 *
 */

public class Question {
	
	private String topic;
	private String question;
	private String a;
	private String b;
	private String c;
	private String d;
	private String correct;
	
	/**
	 * @return		return String topic of question.
	 */
	public String getTopic() { 
		return topic;
	}	
	/**
	 * @param topic		set String topic of question.
	 */
	public void setTopic(String topic) { 
		this.topic = topic;
	}
	
	/**
	 * @return		return String text of question.
	 */
	public String getQuestion() { 
		return question;
	}	
	/**
	 * @param question		set String text of question.
	 */
	public void setQuestion(String question) { 
		this.question = question;
	}
	
	/**
	 * @return		return String option A of question.
	 */
	public String getA() { 
		return a;
	}	
	/**
	 * @param a		set String option A of question.
	 */
	public void setA(String a) { 
		this.a = a;
	}
	
	/**
	 * @return		return String option B of question.
	 */
	public String getB() { 
		return b;
	}	
	/**
	 * @param b		set String option B of question.
	 */
	public void setB(String b) { 
		this.b = b;
	}
	
	/**
	 * @return		return String option C of question.
	 */
	public String getC() { 
		return c;
	}	
	/**
	 * @param c		set String option C of question.
	 */
	public void setC(String c) { 
		this.c = c;
	}
	
	/**
	 * @return		return String option D of question.
	 */
	public String getD() { 
		return d;
	}	
	/**
	 * @param d		set String option D of question.
	 */
	public void setD(String d) { 
		this.d = d;
	}
	
	/**
	 * @return		return String of correct answer.
	 */
	public String getCorrect() { 
		return correct;
	}	
	/**
	 * @param correct		set String of correct answer.
	 */
	public void setCorrect(String correct) { 
		this.correct = correct;
	}

	@Override
    public String toString() {
        return "QuestionObject: \nTopic = " + this.topic + "\nQuestion = " + this.question + "\nA = " + this.a + "\nB = " + this.b +
                "\nC = " + this.c + "\nD = " + this.d + "\nCorrect = " + this.correct + "\n";
    }
}
