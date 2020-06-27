package com.jeff.gift.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jeff.gift.domain.Token;

@Component
public class TokenModelAssembler implements RepresentationModelAssembler<Token, EntityModel<Token>>{

	@Override
	public EntityModel<Token> toModel(Token token) {
		return EntityModel.of(token,
				linkTo(methodOn(GiftController.class).getGiftsInfo(token.getToken())).withSelfRel(),
				linkTo(methodOn(GiftController.class).getGifts(token.getToken())).withRel("gotcha")
		);
	}
}
