package com.moviejsonparsingusingparcelable;

import java.util.List;

public interface IMovieListener {
	void onMoviesListSuccess(List<MovieModel> objects);
	void onMoviesListFailure(String msg);
}
