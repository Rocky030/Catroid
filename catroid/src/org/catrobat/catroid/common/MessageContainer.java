/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.common;

import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.content.BroadcastScript;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MessageContainer {

	private static TreeMap<String, TreeMap<String, Vector<BroadcastScript>>> programReceiverMap = new TreeMap<String, TreeMap<String, Vector<BroadcastScript>>>();
	private static TreeMap<String, ArrayAdapter<String>> programMap = new TreeMap<String, ArrayAdapter<String>>();

	public static void clear() {
		programReceiverMap.clear();
		programMap.clear();
	}

	public static void addMessage(String message) {
		if (message.length() == 0) {
			return;
		}
		String currentProjectName = ProjectManager.getInstance().getCurrentProject().getName();
		if (programReceiverMap.get(currentProjectName) == null) {
			programReceiverMap.put(currentProjectName, new TreeMap<String, Vector<BroadcastScript>>());
		}
		if (!programReceiverMap.get(currentProjectName).containsKey(message)) {
			programReceiverMap.get(currentProjectName).put(message, new Vector<BroadcastScript>());
			addMessageToAdapter(message);
		}
	}

	public static void addMessage(String message, BroadcastScript script) {
		if (message.length() == 0) {
			return;
		}

		String currentProjectName = ProjectManager.getInstance().getCurrentProject().getName();
		if (programReceiverMap.get(currentProjectName).containsKey(message)) {
			programReceiverMap.get(currentProjectName).get(message).add(script);
		} else {
			Vector<BroadcastScript> receiverVec = new Vector<BroadcastScript>();
			receiverVec.add(script);
			programReceiverMap.get(currentProjectName).put(message, receiverVec);
			addMessageToAdapter(message);
		}
	}

	public static void deleteReceiverScript(String message, BroadcastScript script) {
		String currentProjectName = ProjectManager.getInstance().getCurrentProject().getName();
		if (programReceiverMap.get(currentProjectName).containsKey(message)) {
			programReceiverMap.get(currentProjectName).get(message).removeElement(script);
		}
	}

	public static Vector<BroadcastScript> getReceiverOfMessage(String message) {
		return programReceiverMap.get(ProjectManager.getInstance().getCurrentProject().getName()).get(message);
	}

	public static Set<String> getMessages() {
		return programReceiverMap.get(ProjectManager.getInstance().getCurrentProject()).keySet();
	}

	private static synchronized void addMessageToAdapter(String message) {
		ArrayAdapter<String> projectMessageAdapter = programMap.get(ProjectManager.getInstance().getCurrentProject()
				.getName());
		if (projectMessageAdapter != null) {
			projectMessageAdapter.add(message);
		}
		programMap.put(ProjectManager.getInstance().getCurrentProject().getName(), projectMessageAdapter);
	}

	public static synchronized ArrayAdapter<String> getMessageAdapter(Context context) {
		String currentProjectName = ProjectManager.getInstance().getCurrentProject().getName();
		ArrayAdapter<String> projectMessageAdapter = programMap.get(currentProjectName);
		if (projectMessageAdapter == null) {
			projectMessageAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
			projectMessageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			projectMessageAdapter.add(context.getString(R.string.broadcast_nothing_selected));
			if (programReceiverMap.get(currentProjectName) != null) {
				Set<String> messageSet = programReceiverMap.get(currentProjectName).keySet();
				for (String message : messageSet) {
					projectMessageAdapter.add(message);
				}
			}
		}
		return projectMessageAdapter;
	}

	public static int getPositionOfMessageInAdapter(String message) {
		String currentProjectName = ProjectManager.getInstance().getCurrentProject().getName();
		ArrayAdapter<String> projectMessageAdapter = programMap.get(currentProjectName);
		if (projectMessageAdapter == null || programReceiverMap.get(currentProjectName) == null) {
			return -1;
		}
		if (!programReceiverMap.get(currentProjectName).containsKey(message)) {
			return -1;
		}

		return projectMessageAdapter.getPosition(message);
	}
}
