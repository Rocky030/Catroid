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

	private static TreeMap<String, Vector<BroadcastScript>> globalreceiverMap = new TreeMap<String, Vector<BroadcastScript>>();
	//	private static ArrayAdapter<String> globalMessageAdapter = null;
	private static TreeMap<String, ArrayAdapter<String>> projectMap = new TreeMap<String, ArrayAdapter<String>>();

	public static void clear() {
		globalreceiverMap.clear();
		projectMap.clear();
	}

	public static void addMessage(String message) {
		if (message.length() == 0) {
			return;
		}
		if (!globalreceiverMap.containsKey(message)) {
			globalreceiverMap.put(message, new Vector<BroadcastScript>());
			addMessageToAdapter(message);
		}
	}

	public static void addMessage(String message, BroadcastScript script) {
		if (message.length() == 0) {
			return;
		}
		if (globalreceiverMap.containsKey(message)) {
			globalreceiverMap.get(message).add(script);
		} else {
			Vector<BroadcastScript> receiverVec = new Vector<BroadcastScript>();
			receiverVec.add(script);
			globalreceiverMap.put(message, receiverVec);
			addMessageToAdapter(message);
		}
	}

	public static void deleteReceiverScript(String message, BroadcastScript script) {
		if (globalreceiverMap.containsKey(message)) {
			globalreceiverMap.get(message).removeElement(script);
		}
	}

	public static Vector<BroadcastScript> getReceiverOfMessage(String message) {
		return globalreceiverMap.get(message);
	}

	public static Set<String> getMessages() {
		return globalreceiverMap.keySet();
	}

	private static synchronized void addMessageToAdapter(String message) {
		ArrayAdapter<String> projectMessageAdapter = projectMap.get(ProjectManager.getInstance().getCurrentProject()
				.getName());
		if (projectMessageAdapter != null) {
			projectMessageAdapter.add(message);
		}
		projectMap.put(ProjectManager.getInstance().getCurrentProject().getName(), projectMessageAdapter);
	}

	public static synchronized ArrayAdapter<String> getMessageAdapter(Context context) {
		ArrayAdapter<String> projectMessageAdapter = projectMap.get(ProjectManager.getInstance().getCurrentProject()
				.getName());
		if (projectMessageAdapter == null) {
			projectMessageAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
			projectMessageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			projectMessageAdapter.add(context.getString(R.string.broadcast_nothing_selected));
			Set<String> messageSet = globalreceiverMap.keySet();
			for (String message : messageSet) {
				projectMessageAdapter.add(message);
			}
		}
		return projectMessageAdapter;
	}

	public static int getPositionOfMessageInAdapter(String message) {
		if (!globalreceiverMap.containsKey(message)) {
			return -1;
		}

		ArrayAdapter<String> projectMessageAdapter = projectMap.get(ProjectManager.getInstance().getCurrentProject()
				.getName());
		if (projectMessageAdapter == null) {
			return -1;
		}
		return projectMessageAdapter.getPosition(message);
	}
}
