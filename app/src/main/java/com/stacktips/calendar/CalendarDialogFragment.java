package com.stacktips.calendar;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DateInterceptor;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;
import com.stacktips.view.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class CalendarDialogFragment extends DialogFragment {

    private CustomCalendarView calendar;

    public static CalendarDialogFragment show(@NonNull final FragmentManager fragmentManager) {
        final CalendarDialogFragment fragment = new CalendarDialogFragment();

        fragment.show(fragmentManager, null);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_calendar, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_calendar, null);

        calendar = view.findViewById(R.id.calendar);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setMonthTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        calendar.setWeekTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        calendar.setDayTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        calendar.setDecorators(Collections.<DayDecorator>singletonList(new DayDecorator() {
            @Override
            public void decorate(DayView cell) {
                if (calendar.getSelectedDay() != null && CalendarUtils.isSameDay(cell.getDate(), calendar.getSelectedDay())) {
                    cell.setBackgroundResource(R.drawable.ic_calendar_active);
                } else {
                    if (CalendarUtils.isToday(cell.getDate())) {
                        cell.setBackgroundResource(R.drawable.ic_calendar_today);
                    } else if (isBetween(cell.getDate())) {
                        cell.setBackgroundResource(R.drawable.ic_calendar_archive);
                        cell.setTextColor(ContextCompat.getColor(getActivity(), R.color.magenta));
                    } else {
                        cell.setBackgroundResource(android.R.color.transparent);
                    }
                }
            }
        }));

        calendar.setInterceptor(new DateInterceptor() {
            @Override
            public boolean intercept(final Date date) {
                return !isBetween(date);
            }
        });

        calendar.refreshCalendar(Calendar.getInstance());

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Otvoriť archív",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .setNegativeButton("Zrušiť",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();

        final Window window = getDialog().getWindow();

        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }

    private boolean isBetween(Date date) {
        date = CalendarUtils.normalize(date);

        final Date now = CalendarUtils.now();

        return CalendarUtils.isBetween(date, CalendarUtils.addDays(now, -7), now);
    }
}
