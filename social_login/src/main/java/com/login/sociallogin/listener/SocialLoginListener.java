package com.login.sociallogin.listener;

import com.login.sociallogin.networks.SocialNetwork;

/**
 * Created by danhtran on 05/04/2017.
 */
public interface SocialLoginListener {
    void onSuccess(SocialNetwork socialNetwork);

    void onFailure(SocialNetwork socialNetwork, Object error);
}
