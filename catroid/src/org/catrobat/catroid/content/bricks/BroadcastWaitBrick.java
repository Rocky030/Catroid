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
package org.catrobat.catroid.content.bricks;

import java.util.List;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.common.MessageContainer;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ExtendedActions;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.ui.dialogs.BrickTextDialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class BroadcastWaitBrick extends BrickBaseType {
	private static final long serialVersionUID = 1L;
	private String broadcastMessage = "";
	private BroadcastScript waitScript;

	public BroadcastWaitBrick() {

	}

	public BroadcastWaitBrick(Sprite sprite) {
		this.sprite = sprite;
	}

	public void setSelectedMessage(String selectedMessage) {
		this.broadcastMessage = selectedMessage;
		MessageContainer.addMessage(this.broadcastMessage);
	}

	public String getBroadcastMessage() {
		return broadcastMessage;
	}

	public BroadcastScript getWaitScript() {
		return waitScript;
	}

	public void setWaitScript(BroadcastScript waitScript) {
		this.waitScript = waitScript;
	}

	private Object readResolve() {
		if (broadcastMessage != null && ProjectManager.getInstance().getCurrentProject() != null) {
			MessageContainer.addMessage(broadcastMessage);
		}
		return this;
	}

	@Override
	public View getView(final Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}

		view = View.inflate(context, R.layout.brick_broadcast_wait, null);
		view = getViewWithAlpha(alphaValue);

		setCheckboxView(R.id.brick_broadcast_wait_checkbox);
		final Brick brickInstance = this;

		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				checked = isChecked;
				adapter.handleCheck(brickInstance, isChecked);
			}
		});
		final Spinner broadcastSpinner = (Spinner) view.findViewById(R.id.brick_broadcast_wait_spinner);
		broadcastSpinner.setAdapter(MessageContainer.getMessageAdapter(context));
		if (!(checkbox.getVisibility() == View.VISIBLE)) {
			broadcastSpinner.setClickable(true);
			broadcastSpinner.setEnabled(true);
		} else {
			broadcastSpinner.setClickable(false);
			broadcastSpinner.setEnabled(false);
		}

		broadcastSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			private boolean start = true;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if (start) {
					start = false;
					return;
				}
				broadcastMessage = ((String) parent.getItemAtPosition(pos)).trim();
				if (broadcastMessage == context.getString(R.string.broadcast_nothing_selected)) {
					broadcastMessage = "";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		int position = MessageContainer.getPositionOfMessageInAdapter(broadcastMessage);
		if (position > 0) {
			broadcastSpinner.setSelection(position);
		}

		Button newBroadcastMessage = (Button) view.findViewById(R.id.brick_broadcast_wait_button_new_message);
		newBroadcastMessage.setClickable(true);
		newBroadcastMessage.setFocusable(true);
		newBroadcastMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (checkbox.getVisibility() == View.VISIBLE) {
					return;
				}
				ScriptActivity activity = (ScriptActivity) context;

				BrickTextDialog editDialog = new BrickTextDialog() {
					@Override
					protected void initialize() {
					}

					@Override
					protected boolean handleOkButton() {
						String newMessage = (input.getText().toString()).trim();
						if (newMessage.length() == 0
								|| newMessage.equals(context.getString(R.string.broadcast_nothing_selected))) {
							dismiss();
							return false;
						}
						broadcastMessage = newMessage;
						MessageContainer.addMessage(broadcastMessage);
						int position = MessageContainer.getPositionOfMessageInAdapter(broadcastMessage);

						broadcastSpinner.setSelection(position);

						return true;
					}
				};

				editDialog.show(activity.getSupportFragmentManager(), "dialog_broadcast_wait_brick");
			}
		});
		return view;
	}

	@Override
	public View getPrototypeView(Context context) {
		return View.inflate(context, R.layout.brick_broadcast_wait, null);
	}

	@Override
	public View getViewWithAlpha(int alphaValue) {
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.brick_broadcast_wait_layout);
		Drawable background = layout.getBackground();
		background.setAlpha(alphaValue);
		this.alphaValue = (alphaValue);
		return view;
	}

	@Override
	public Brick clone() {
		return new BroadcastWaitBrick(sprite);
	}

	@Override
	public List<SequenceAction> addActionToSequence(SequenceAction sequence) {
		sequence.addAction(ExtendedActions.broadcastFromWaiter(sprite, broadcastMessage));
		return null;
	}

}
