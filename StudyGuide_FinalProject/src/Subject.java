/**
 * @author Y.Gomez, J.Stec
 */

import java.util.ArrayList;

public class Subject {
	ArrayList<String> subjects;
	
	/**
	 * initiate ArrayList of subjects.
	 */
	public Subject(){
		subjects = new ArrayList<String>();
	}
	
	/**
	 * @param subj		add String of subject of subjects ArrayList.
	 */
	public void add(String subj){
		subjects.add(subj);
	}
	
	/**
	 * @param subj		String of subject to check if ArrayList contains it.
	 * @return			return true/false if ArrayList contains subj.
	 */
	public boolean contains(String subj){
		if (subjects.contains(subj))
			return true;
		else
			return false;
	}
	
	/**
	 * @return		return String of all subjects.
	 */
	public String getSubjects(){
		String list = "";
		for (int i = 0; i < subjects.size(); i++){
			if ((i+1) == subjects.size())
				list += subjects.get(i);
			else
				list += subjects.get(i) + ", ";
		}
		return list;
	}
}
